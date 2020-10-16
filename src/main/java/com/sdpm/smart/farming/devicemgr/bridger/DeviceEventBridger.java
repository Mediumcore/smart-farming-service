package com.sdpm.smart.farming.devicemgr.bridger;

import com.sdpm.smart.farming.common.constant.DeviceEventTypeEnum;
import com.sdpm.smart.farming.common.constant.DeviceStatus;
import com.sdpm.smart.farming.devicemgr.entity.Device;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEvent;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import com.sdpm.smart.farming.devicemgr.message.DeviceMessageProto;
import com.sdpm.smart.farming.devicemgr.repository.DeviceEventRepository;
import com.sdpm.smart.farming.devicemgr.service.DeviceEventTypeService;
import com.sdpm.smart.farming.devicemgr.service.DeviceService;
import com.sdpm.smart.farming.devicemgr.service.RealTimeEventsService;
import com.sdpm.smart.farming.mqtt.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 设备事件从EMQ桥接到MySQL
 *
 * @author rukey
 */
@Component
public class DeviceEventBridger extends MessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(DeviceEventBridger.class);
    @Autowired
    private DeviceEventTypeService eventTypeService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceEventRepository eventRepository;
    @Autowired
    private RealTimeEventsService realTimeEventsService;

    @Override
    public void handle(String topic, DeviceMessageProto.DeviceMessage message) {

        // 设备ID
        Integer deviceId = Integer.valueOf(topic.substring(topic.lastIndexOf('/') + 1));
        Device device = deviceService.findDeviceById(deviceId);

        // disable状态不处理信息
        if (DeviceStatus.DISABLE.equals(device.getStatus())) {
            logger.warn("The current device {} is in a disable state.", device.getId());
            return;
        }
        String eventType = message.getType();
        switch (eventType) {
            case DeviceEventTypeEnum.CONTROL_STATE:
                // todo
            default:
                DeviceEventType deviceEventType;
                try {
                    deviceEventType = eventTypeService.findEventTypeByType(eventType);
                } catch (Exception e) {
                    logger.warn("Unknown event type,{}", eventType);
                    return;
                }
                // 事件推送
                realTimeEventsService.sendEvent(device, deviceEventType, message.getBody().toStringUtf8(), message.getCreated());
                // 事件入库
                event2db(device, deviceEventType, message);

        }

    }

    @Async
    public void event2db(Device device, DeviceEventType eventType, DeviceMessageProto.DeviceMessage deviceEventMessage) {
        DeviceEvent event = new DeviceEvent();
        event.setDeviceId(device.getId());
        event.setTypeId(eventType.getId());
        event.setBody(deviceEventMessage.getBody().toStringUtf8());
        event.setCreated(deviceEventMessage.getCreated());
        eventRepository.save(event);
    }
}
