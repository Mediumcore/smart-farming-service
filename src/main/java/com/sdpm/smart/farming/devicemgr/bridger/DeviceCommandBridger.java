package com.sdpm.smart.farming.devicemgr.bridger;

import com.sdpm.smart.farming.common.constant.DeviceStatus;
import com.sdpm.smart.farming.devicemgr.entity.*;
import com.sdpm.smart.farming.devicemgr.message.DeviceMessageProto;
import com.sdpm.smart.farming.devicemgr.repository.DeviceCommandRepository;
import com.sdpm.smart.farming.devicemgr.service.DeviceCommandTypeService;
import com.sdpm.smart.farming.devicemgr.service.DeviceService;
import com.sdpm.smart.farming.mqtt.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 设备指令从EMQ桥接到MySQL
 *
 * @author rukey
 */
@Component
public class DeviceCommandBridger extends MessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeviceCommandBridger.class);
    @Autowired
    private DeviceCommandTypeService commandTypeService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceCommandRepository realTimeCommandRepository;

    @Override
    public void handle(String topic, DeviceMessageProto.DeviceMessage message) {
        // 设备ID
        Integer deviceId = Integer.valueOf(topic.substring(topic.lastIndexOf('/')+1));
        Device device = deviceService.findDeviceById(deviceId);

        // disable状态不处理信息
        if (DeviceStatus.DISABLE.equals(device.getStatus())) {
            logger.warn("The current device {} is in a disable state.", device.getId());
            return;
        }
        String messageType = message.getType();
        DeviceCommandType commandType = commandTypeService.findCommandTypeByType(messageType);
        command2db(device, commandType, message);

    }

    public void command2db(Device device, DeviceCommandType commandType, DeviceMessageProto.DeviceMessage commandMessage) {
        DeviceCommand command = new DeviceCommand();
        command.setDeviceId(device.getId());
        command.setTypeId(commandType.getId());
        command.setBody(commandMessage.getBody().toStringUtf8());
        command.setCreated(commandMessage.getCreated());
        realTimeCommandRepository.save(command);
    }
}
