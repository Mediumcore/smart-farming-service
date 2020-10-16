package com.sdpm.smart.farming.devicemgr.service;

import com.sdpm.smart.farming.common.constant.DeviceStatus;
import com.sdpm.smart.farming.common.exception.SmartFarmingServiceException;
import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import com.sdpm.smart.farming.devicemgr.entity.Device;
import com.sdpm.smart.farming.devicemgr.entity.DeviceRecord;
import com.sdpm.smart.farming.devicemgr.entity.DeviceType;
import com.sdpm.smart.farming.devicemgr.repository.DeviceCommandRepository;
import com.sdpm.smart.farming.devicemgr.repository.DeviceEventRepository;
import com.sdpm.smart.farming.devicemgr.repository.DeviceRecordRepository;
import com.sdpm.smart.farming.devicemgr.repository.DeviceRepository;
import com.sdpm.smart.farming.devicemgr.vo.DeviceVO;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author rukey
 */
@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceTypeService deviceTypeService;

    @Autowired
    private DeviceCommandRepository commandRepository;
    @Autowired
    private DeviceEventRepository eventRepository;
    @Autowired
    private DeviceRecordRepository recordRepository;

    @Value("${mqtt.device-client-id-prefix}")
    private String deviceClientIdPrefix;

    @Value("${mqtt.rtc-video-client-id-prefix}")
    private String rtcVideoClientIdPrefix;

    @Value("${mqtt.rtc-data-client-id-prefix}")
    private String rtcDataClientIdPrefix;

    @Value("${mqtt.event-topic-prefix}")
    private String eventTopicPrefix;

    @Value("${mqtt.command-topic-prefix}")
    private String commandTopicPrefix;

    @Value("${mqtt.rtc-video-topic-prefix}")
    private String rtcVideoTopicPrefix;

    @Value("${mqtt.rtc-data-topic-prefix}")
    private String rtcDataTopicPrefix;

    @Value("${mqtt.outerurl}")
    private String mqttUrl;
    @Value("${mqtt.qos}")
    private Integer mqttQos;
    @Value("${mqtt.username}")
    private String mqttUsername;
    @Value("${mqtt.password}")
    private String mqttPassword;

    @Value("${ice.server.urls}")
    private String iceServerUrls;
    @Value("${ice.server.username}")
    private String iceServerUsername;
    @Value("${ice.server.credential}")
    private String iceServerCredential;


    public Page<Device> findDevicesWithPage(String deviceName, String deviceType, Pageable pageable) {
        return deviceRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.hasLength(deviceName)) {
                list.add(cb.like(root.get("name"), "%" + deviceName + "%"));
            }
            if (StringUtils.hasLength(deviceType)) {
                list.add(cb.equal(root.get("type").get("type"), deviceType));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, pageable);
    }

    @Cacheable(value = "device", key = "#deviceId")
    public Device findDeviceById(Integer deviceId) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceId);
        if (!deviceOptional.isPresent()) {
            throw new SmartFarmingServiceException("设备不存在。");
        }
        return deviceOptional.get();
    }

    public Device addDevice(String name, String description, String type) {
        Device device = new Device();
        device.setName(name);
        device.setDescription(description);
        device.setStatus(DeviceStatus.PENDING);
        device.setType(deviceTypeService.findDeviceTypeByType(type));
        return saveDevice(device);
    }


    public Device updateDevice(Integer id, String name, String description) {
        Device device = findDeviceById(id);
        device.setName(name);
        device.setDescription(description);
        return saveDevice(device);
    }

    @CachePut(value = "device", key = "#device.id")
    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Transactional
    @CacheEvict(value = "device", key = "#id")
    public void removeDevice(Integer id) {
        Device device = findDeviceById(id);
        // 删除指令
        commandRepository.deleteAllByDeviceId(id);
        // 删除事件
        eventRepository.deleteAllByDeviceId(id);
        // 删除绑定
        Integer sourceId = device.getSourceId();
        if (sourceId != null) {
            Device sourceDevice = findDeviceById(sourceId);
            sourceDevice.setTargetId(null);
            saveDevice(sourceDevice);
        }
        deviceRepository.deleteById(device.getId());
    }

    /**
     * 获取设备接入凭证
     *
     * @param id id
     * @return 接入凭证
     */
    public CertificateProto.Certificate getDeviceCertificate(Integer id) {
        Device device = findDeviceById(id);
        return CertificateProto.Certificate.newBuilder()
                .setClientId(deviceClientIdPrefix + device.getId())
                .setDeviceId(device.getId())
                .setQos(mqttQos)
                .setCommandTopic(commandTopicPrefix + device.getId())
                .setEventTopic(eventTopicPrefix + device.getId())
                .setUrl(mqttUrl)
                .setUsername(mqttUsername)
                .setPassword(mqttPassword)
                .setRtcVideoClientId(rtcVideoClientIdPrefix + device.getId())
                .setRtcVideoReqTopic(rtcVideoTopicPrefix + "req/" + device.getId())
                .setRtcVideoAnsTopic(rtcVideoTopicPrefix + "ans/" + device.getId())
                .setRtcDataClientId(rtcDataClientIdPrefix + device.getId())
                .setRtcDataReqTopic(rtcDataTopicPrefix + "req/" + device.getId())
                .setRtcDataAnsTopic(rtcDataTopicPrefix + "ans/" + device.getId())
                .setIceServerUrls(iceServerUrls)
                .setIceServerUsername(iceServerUsername)
                .setIceServerCredential(iceServerCredential)
                .build();
    }

    public Page<DeviceRecord> findDeviceRecords(Integer deviceId, String timeType, Long startTime, Long endTime, Pageable pageable) {
        return recordRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (Objects.nonNull(deviceId) && deviceId > 0) {
                list.add(cb.equal(root.get("device").get("id"), deviceId));
            }
            if ("offlineTime".equals(timeType) || "onlineTime".equals(timeType)) {
                if (Objects.nonNull(startTime) && startTime > 0) {
                    list.add(cb.greaterThanOrEqualTo(root.get(timeType), startTime));
                }
                if (Objects.nonNull(endTime) && endTime > 0) {
                    list.add(cb.lessThanOrEqualTo(root.get(timeType), endTime));
                }
            }

            return cb.and(list.toArray(new Predicate[0]));
        }, pageable);
    }

    public String onlineProportionByType(Integer typeId) {
        // 设备总数
        Long count = deviceRepository.count((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (Objects.nonNull(typeId) && typeId > 0) {
                list.add(cb.equal(root.get("type").get("id"), typeId));
            }
            return cb.and(list.toArray(new Predicate[0]));
        });
        // 设备在线数
        Long onlineCount = deviceRepository.count((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (Objects.nonNull(typeId) && typeId > 0) {
                list.add(cb.equal(root.get("type").get("id"), typeId));
            }
            list.add(cb.equal(root.get("status"), "online"));
            return cb.and(list.toArray(new Predicate[0]));
        });
        return String.format("%d/%d", onlineCount, count);
    }


}
