package com.sdpm.smart.farming.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * MQTT Client
 *
 * @author rukey
 */
@Component
public class MQTTClient implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(MQTTClient.class);
    @Value("${mqtt.url}")
    private String mqttUrl;
    @Value("${mqtt.client-id}")
    private String mqttClientId;
    @Value("${mqtt.username}")
    private String mqttUsername;
    @Value("${mqtt.password}")
    private String mqttPassword;
    @Value("${mqtt.file-persistence-path}")
    private String filePersistencePath;
    private MqttClient client;
    private MessageAsyncDistributor messageAsyncDistributor;


    @PostConstruct
    public void setup() {
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(filePersistencePath);
        // connect info
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(mqttUsername);
        connectOptions.setPassword(mqttPassword.toCharArray());
        connectOptions.setAutomaticReconnect(true);

        try {
            client = new MqttClient(mqttUrl, mqttClientId, dataStore);
            client.connect(connectOptions);
            client.setCallback(this);
            logger.info("connected");
        } catch (MqttException e) {
            logger.error("connect to mqtt error:{}", e.getMessage());
        }
        this.messageAsyncDistributor = new MessageAsyncDistributor();
        Thread messageAsyncDistributorThread = new Thread(messageAsyncDistributor, "message-async-distributor-thread");
        messageAsyncDistributorThread.start();
    }

    /**
     * publish message
     *
     * @param topicName topic
     * @param qos       qos
     * @param payload   context
     * @throws MqttException e
     */
    public void publish(String topicName, int qos, byte[] payload) throws MqttException {
        // Create and configure a message
        MqttMessage message = new MqttMessage(payload);
        message.setQos(qos);
        client.publish(topicName, message);
    }

    /**
     * subscribe message
     *
     * @param topicName topic
     * @param qos       qos
     * @throws MqttException e
     */
    public void subscribe(String topicName, int qos, MessageHandler handler) throws MqttException {
        client.subscribe(topicName, qos);
        messageAsyncDistributor.subscribeMessageHandler(topicName, handler);
    }

    /**
     * cancel subscribe
     *
     * @param topicName topic
     * @throws MqttException e
     */
    public void unsubscribe(String topicName) throws MqttException {
        client.unsubscribe(topicName);
        messageAsyncDistributor.unsubscribeMessageHandler(topicName);
    }

    @Override
    public void connectionLost(Throwable cause) {
        // called when connect lost
        logger.warn("The client connection is lost.");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        this.messageAsyncDistributor.distribute(new MessageWithTopic(topic, message));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // called when delivery for a message has been completed
    }
}

