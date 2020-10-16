package com.sdpm.smart.farming.devicemgr.initializer;

import com.alibaba.fastjson.JSON;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommandType;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import com.sdpm.smart.farming.devicemgr.entity.DeviceType;
import com.sdpm.smart.farming.devicemgr.repository.DeviceCommandTypeRepository;
import com.sdpm.smart.farming.devicemgr.repository.DeviceEventTypeRepository;
import com.sdpm.smart.farming.devicemgr.repository.DeviceTypeRepository;
import com.sdpm.smart.farming.devicemgr.service.WebHookService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * 初始化指令，事件类型，设备类型
 *
 * @author rukey
 */
@Component
public class DeviceInitializer {
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;
    @Autowired
    private DeviceCommandTypeRepository commandTypeRepository;
    @Autowired
    private DeviceEventTypeRepository eventTypeRepository;

    private static final Logger logger = LoggerFactory.getLogger(DeviceInitializer.class);

    public void init() {
        String templatePath = "template.json";
        TemplateEntity templateEntity = JSON.parseObject(readTemplate(templatePath), TemplateEntity.class);
        // 初始化设备类型
        logger.info("Initializing device type...");
        List<DeviceType> deviceTypes = templateEntity.getDevices();
        deviceTypes.forEach(deviceType -> {
            DeviceType existDeviceType = deviceTypeRepository.findByType(deviceType.getType());
            if (Objects.isNull(existDeviceType)) {
                deviceTypeRepository.save(deviceType);
                logger.info("Add device type {}.", deviceType.getType());
            } else {
                logger.warn("Device type {} already exists.", deviceType.getType());
            }
        });

        // 初始化指令类型
        logger.info("Initializing command type...");
        List<DeviceCommandType> commandTypes = templateEntity.getCommands();
        commandTypes.forEach(commandType -> {
            DeviceCommandType existCommandType = commandTypeRepository.findByType(commandType.getType());
            if (Objects.isNull(existCommandType)) {
                commandTypeRepository.save(commandType);
                logger.info("Add command type {}.", commandType.getType());
            } else {
                logger.warn("Command type {} already exists.", commandType.getType());
            }
        });

        // 初始化事件类型
        logger.info("Initializing event type...");
        List<DeviceEventType> eventTypes = templateEntity.getEvents();
        eventTypes.forEach(eventType -> {
            DeviceEventType existEventType = eventTypeRepository.findByType(eventType.getType());
            if (Objects.isNull(existEventType)) {
                eventTypeRepository.save(eventType);
                logger.info("Add event type [{}].", eventType.getType());
            } else {
                logger.warn("Event type [{}] already exists.", eventType.getType());
            }
        });


    }

    public String readTemplate(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        String template = "";
        try {

            template = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return template;
    }
}
