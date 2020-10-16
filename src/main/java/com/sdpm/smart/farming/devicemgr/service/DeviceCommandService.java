package com.sdpm.smart.farming.devicemgr.service;

import com.google.protobuf.ByteString;
import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import com.sdpm.smart.farming.devicemgr.entity.Device;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommand;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommandType;
import com.sdpm.smart.farming.devicemgr.message.DeviceMessageProto;
import com.sdpm.smart.farming.devicemgr.repository.DeviceCommandRepository;
import com.sdpm.smart.farming.mqtt.MQTTClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author shirukai
 */
@Service
public class DeviceCommandService {
    @Autowired
    private DeviceCommandRepository commandRepository;
    @Autowired
    private MQTTClient mqttClient;
    @Autowired
    private DeviceCommandTypeService commandTypeService;
    @Autowired
    private DeviceService deviceService;
    private static final Logger logger = LoggerFactory.getLogger(DeviceCommandService.class);

    public Page<DeviceCommand> queryDeviceCommand(Integer deviceId, Integer commandTypeId, Long startTime, Long endTime, Pageable pageable) {
        return commandRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (Objects.nonNull(deviceId) && deviceId > 0) {
                list.add(cb.equal(root.get("deviceId"), deviceId));
            }
            if (Objects.nonNull(commandTypeId) && commandTypeId > 0) {
                list.add(cb.equal(root.get("typeId"), commandTypeId));
            }
            if (Objects.nonNull(startTime) && startTime > 0) {
                list.add(cb.greaterThanOrEqualTo(root.get("created"), startTime));
            }
            if (Objects.nonNull(endTime) && endTime > 0) {
                list.add(cb.lessThanOrEqualTo(root.get("created"), endTime));
            }

            return cb.and(list.toArray(new Predicate[0]));
        }, pageable);
    }

    public Long commandCount() {
        return commandRepository.count();
    }

    /**
     * 发送命令到目标设备
     *
     * @param type           命令类型
     * @param payload        命令内容
     * @param targetDeviceId 目标设备ID
     */
    public void sendCommand(String type, String payload, Integer targetDeviceId) {
        // 校验类型是否存在
        commandTypeService.findCommandTypeByType(type);
        // 获取设备的接入凭证
        CertificateProto.Certificate certificate = deviceService.getDeviceCertificate(targetDeviceId);
        // 获取当前系统时间
        long created = System.currentTimeMillis();
        DeviceMessageProto.DeviceMessage message = DeviceMessageProto.DeviceMessage.newBuilder()
                .setCreated(created)
                .setType(type)
                .setBody(ByteString.copyFromUtf8(payload))
                .build();

        // 发送数据
        try {
            mqttClient.publish(certificate.getCommandTopic(), certificate.getQos(), message.toByteArray());
            logger.info("Successfully send command to device {}.Message: type={},payload={},created={}", targetDeviceId, type, payload, created);
        } catch (MqttException e) {
            logger.error("Failed to send command to device {}", targetDeviceId, e);
        }

    }
}
