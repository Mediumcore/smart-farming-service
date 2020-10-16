package com.sdpm.smart.farming.devicemgr.initializer;

import com.sdpm.smart.farming.devicemgr.bridger.DeviceCommandBridger;
import com.sdpm.smart.farming.devicemgr.bridger.DeviceEventBridger;
import com.sdpm.smart.farming.mqtt.MQTTClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 数据桥接初始化器
 * @author rukey
 */
@Component
public class BridgerInitializer {
    @Value("${mqtt.listen-topics.events}")
    private String eventTopics;
    @Value("${mqtt.listen-topics.commands}")
    private String commandTopics;
    @Value("${mqtt.qos}")
    private Integer qos;
    @Autowired
    private MQTTClient mqttClient;

    @Autowired
    private DeviceCommandBridger commandBridger;

    @Autowired
    private DeviceEventBridger eventBridger;

    public void init() {
        try {
            mqttClient.subscribe(eventTopics, qos, eventBridger);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        try {
            mqttClient.subscribe(commandTopics,qos,commandBridger);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
