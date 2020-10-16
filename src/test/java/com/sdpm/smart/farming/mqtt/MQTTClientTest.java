package com.sdpm.smart.farming.mqtt;

import org.apache.commons.lang3.RandomStringUtils;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author rukey
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MQTTClientTest {
    @Autowired
    private MQTTClient mqttClient;

    @Test
    public void testClient() throws MqttException, InterruptedException {
//        mqttClient.subscribe("test", 2, new MessageHandler() {
//            @Override
//            public void handle(String topic, MqttMessage message) {
//                System.out.println(new String(message.getPayload()));
//            }
//        });
        byte[] payload = RandomStringUtils.random(10240).getBytes();
        System.out.println("单条消息字节长度："+(payload.length));
        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
//            mqttClient.publish("test-speed", 0, payload);
//        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时: " + (endTime - startTime) + "ms");
    }
}