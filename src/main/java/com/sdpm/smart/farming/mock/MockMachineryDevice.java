package com.sdpm.smart.farming.mock;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

/**
 * 模拟机械设备
 *
 * @author rukey
 */
public class MockMachineryDevice {
    private static final Logger logger = LoggerFactory.getLogger(MockMachineryDevice.class);

    public static void main(String[] args) throws MqttException, InvalidProtocolBufferException, InterruptedException {
        //String certificateStr = "CAESGHRjcDovLzE1NC44LjIyMi4xNTI6MTg4MxgBIgllbXF4MTIzNCEqBGVtcXgyFXNmcy1kZXZpY2UtNTFiMjBlYjMtMToVc2ZzLzUxYjIwZWIzL2V2ZW50cy8xQhdzZnMvNTFiMjBlYjMvY29tbWFuZHMvMQ==";
        String certificateStr = "CAISGHRjcDovLzE1NC44LjIyMi4xNTI6MTg4MxgBIgllbXF4MTIzNCEqBGVtcXgyFXNmcy1kZXZpY2UtNTFiMjBlYjMtMjoVc2ZzLzUxYjIwZWIzL2V2ZW50cy8yQhdzZnMvNTFiMjBlYjMvY29tbWFuZHMvMkoYc2ZzLXJ0Yy12aWRlby01MWIyMGViMy0yUhxzZnMvNTFiMjBlYjMvcnRjL3ZpZGVvL3JlcS8yWhxzZnMvNTFiMjBlYjMvcnRjL3ZpZGVvL2Fucy8yYhdzZnMtcnRjLWRhdGEtNTFiMjBlYjMtMmobc2ZzLzUxYjIwZWIzL3J0Yy9kYXRhL3JlcS8ychtzZnMvNTFiMjBlYjMvcnRjL2RhdGEvYW5zLzJ6F3R1cm46MTU0LjguMjIyLjE1MjozNDc4ggEHa3VyZW50b4oBDGt1cmVudG8hMTIzNA==";
        CertificateProto.Certificate certificate = CertificateProto.Certificate.parseFrom(Base64Utils.decodeFromString(certificateStr));
        String mqttUrl = certificate.getUrl();
        String username = certificate.getUsername();
        String password = certificate.getPassword();
        String clientId = certificate.getClientId();
        MqttClient client = MQTTClientUtil.createClient(mqttUrl, clientId, username, password);

        // 起一个线程模拟发送速度事件
        BaseEventMocker velocityMocker = new VelocityEventMocker(client, certificate).setInterval(1500);

        // 起一个线程模拟发送刹车事件
        BaseEventMocker breakMocker = new BrakeEventMocker(client, certificate).setInterval(5000);

        // 起一个线程模拟发送油门事件
        BaseEventMocker throttleMocker = new ThrottleEventMocker(client, certificate);

        // 起一个线程模拟发送控制状态
        BaseEventMocker controlStateMocker = new ControlStateEventMocker(client, certificate).setInterval(20000);

        // 起一个线程模拟发送位置信息
        BaseEventMocker locationMocker = new LocationEventMocker(client, certificate).setInterval(2000);

        velocityMocker.start();
        breakMocker.start();
        throttleMocker.start();
        controlStateMocker.start();
        locationMocker.start();

        controlStateMocker.join();
        client.disconnect();
        client.close();
    }

}
