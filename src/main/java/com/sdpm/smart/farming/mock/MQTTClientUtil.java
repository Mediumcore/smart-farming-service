package com.sdpm.smart.farming.mock;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 * MQTT客户端工具类
 *
 * @author rukey
 */
public class MQTTClientUtil {
    public static MqttClient createClient(String url, String clientId, String username, String password) throws MqttException {
        MemoryPersistence dataStore = new MemoryPersistence();
        // connect info
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(username);
        connectOptions.setPassword(password.toCharArray());
        connectOptions.setAutomaticReconnect(true);

        MqttClient client = new MqttClient(url, clientId, dataStore);
        client.connect(connectOptions);
        return client;
    }
}
