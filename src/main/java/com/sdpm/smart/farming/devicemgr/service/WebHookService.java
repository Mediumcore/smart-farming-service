package com.sdpm.smart.farming.devicemgr.service;

import com.sdpm.smart.farming.common.constant.DeviceEventTypeEnum;
import com.sdpm.smart.farming.common.constant.DeviceStatus;
import com.sdpm.smart.farming.common.constant.WebHookType;
import com.sdpm.smart.farming.devicemgr.entity.Device;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;

import com.sdpm.smart.farming.devicemgr.entity.DeviceEvent;
import com.sdpm.smart.farming.devicemgr.entity.DeviceRecord;
import com.sdpm.smart.farming.devicemgr.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;


/**
 * WebHookService
 *
 * @author rukey
 */
@Service
public class WebHookService {
    private static final Logger logger = LoggerFactory.getLogger(WebHookService.class);
    private static final String CONTROL_DEVICE_TYPE = "controller";
    @Value("sfs-device-${sfs.token}")
    private String clientPrefix;

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceEventTypeService eventTypeService;
    @Autowired
    private DeviceEventRepository eventRepository;
    @Autowired
    private DeviceRecordRepository deviceRecordRepository;

    @Autowired
    private RealTimeEventsService realTimeEventsService;

    @Autowired
    private OperatingService operatingService;


    public void handleWebHook(Map<String, String> params) {
        String action = params.getOrDefault("action", "unknown");
        String clientId = params.get("clientid");
        // 过滤掉不匹配规则的设备客户端
        if (!clientId.startsWith(clientPrefix)) {
            logger.warn("Device client {} does not need to do anything.", clientId);
            return;
        }
        Integer deviceId = Integer.valueOf(clientId.substring(clientPrefix.length() + 1));
        // 过滤不存在的设备客户端
        Device device;
        try {
            device = deviceService.findDeviceById(deviceId);
        } catch (Exception e) {
            logger.warn("Device {} does not need to do anything.", deviceId);
            return;
        }
        // 过滤已经禁用的设备
        if (DeviceStatus.DISABLE.equals(device.getStatus())) {
            logger.warn("Device {} has been disabled.", deviceId);
            return;
        }
        switch (action) {
            case WebHookType.CONNECTED:
                onClientConnected(device, params);
                break;
            case WebHookType.DISCONNECTED:
                onClientDisConnected(device, params);
                break;
            // todo for other webhook event.
            default:
                logger.warn("WebHook Action {} does not need to do anything.", action);
        }
    }

    @Transactional
    public void onClientConnected(Device device, Map<String, String> params) {
        operatingService.sendBindingCommand(device);
        String ipaddress = params.get("ipaddress");
        device.setIpaddress(ipaddress);
        device.setStatus(DeviceStatus.ONLINE);
        // 获取当前的日期
        Long currentTime = System.currentTimeMillis();
        device.setOnlineTime(currentTime);
        // 判断下线时间是否为空，如果不为空，添加设备记录
        if (Objects.nonNull(device.getOfflineTime()) && device.getOfflineTime() > 0) {
            DeviceRecord deviceRecord = new DeviceRecord();
            deviceRecord.setDevice(device);
            deviceRecord.setOnlineTime(device.getOnlineTime());
            deviceRecord.setOfflineTime(device.getOfflineTime());
            deviceRecord.setIpaddress(device.getIpaddress());
            deviceRecordRepository.save(deviceRecord);
            // 将下线时间值为空
            device.setOfflineTime(null);
        }
        deviceService.saveDevice(device);
        // 上线事件
        emitEvent2db(DeviceEventTypeEnum.ONLINE, device, currentTime);


    }

    public void onClientDisConnected(Device device, Map<String, String> params) {
        device.setStatus(DeviceStatus.OFFLINE);
        // 获取当前的日期
        Long currentTime = System.currentTimeMillis();
        device.setOfflineTime(currentTime);
        deviceService.saveDevice(device);
        // 发送下线事件
        emitEvent2db(DeviceEventTypeEnum.OFFLINE, device, currentTime);

    }

    public void emitEvent2db(String type, Device device, Long created) {
        DeviceEventType eventType = eventTypeService.findEventTypeByType(type);
        DeviceEvent event = new DeviceEvent();
        event.setDeviceId(device.getId());
        event.setCreated(created);
        event.setTypeId(eventType.getId());
        realTimeEventsService.sendEvent(device, eventType, "", created);
        eventRepository.save(event);
    }

}
