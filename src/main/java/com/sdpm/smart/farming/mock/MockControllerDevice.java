package com.sdpm.smart.farming.mock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import com.sdpm.smart.farming.devicemgr.message.DeviceMessageProto;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 模拟控制设备
 *
 * @author rukey
 */
public class MockControllerDevice {
    private static final Logger logger = LoggerFactory.getLogger(MockControllerDevice.class);
    private static final String BINDS_TYPE = "binds";
    private static CertificateProto.Certificate targetDeviceCert = null;
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws MqttException, InvalidProtocolBufferException, InterruptedException {
        String certificateStr = "CAMSGHRjcDovLzE1NC44LjIyMi4xNTI6MTg4MxgBIgllbXF4MTIzNCEqBGVtcXgyFXNmcy1kZXZpY2UtNTFiMjBlYjMtMzoVc2ZzLzUxYjIwZWIzL2V2ZW50cy8zQhdzZnMvNTFiMjBlYjMvY29tbWFuZHMvM0oYc2ZzLXJ0Yy12aWRlby01MWIyMGViMy0zUhxzZnMvNTFiMjBlYjMvcnRjL3ZpZGVvL3JlcS8zWhxzZnMvNTFiMjBlYjMvcnRjL3ZpZGVvL2Fucy8zYhdzZnMtcnRjLWRhdGEtNTFiMjBlYjMtM2obc2ZzLzUxYjIwZWIzL3J0Yy9kYXRhL3JlcS8zchtzZnMvNTFiMjBlYjMvcnRjL2RhdGEvYW5zLzM";
        CertificateProto.Certificate certificate = CertificateProto.Certificate.parseFrom(Base64Utils.decodeFromString(certificateStr));
        System.out.println(certificate);
//        String mqttUrl = certificate.getUrl();
//        String username = certificate.getUsername();
//        String password = certificate.getPassword();
//        String clientId = certificate.getClientId();
//        MqttClient client = MQTTClientUtil.createClient(mqttUrl, clientId, username, password);
//        // 监听指令topic
//        logger.info(certificate.getCommandTopic());
//        client.subscribe(certificate.getCommandTopic(), (topic, message) -> {
//            DeviceMessageProto.DeviceMessage deviceMessage = DeviceMessageProto.DeviceMessage.parseFrom(message.getPayload());
//            String type = deviceMessage.getType();
//            switch (type) {
//                case BINDS_TYPE:
//                    handleBindCommand(deviceMessage.getBody().toStringUtf8());
//                    break;
//                default:
//                    break;
//
//            }
//        });
//        logger.info("等待设备绑定指令的下发...");
//        latch.await();
//        logger.info("模拟发送指令...");
//        // 模拟自动驾驶指令发送
//        BaseCommandMocker autoDriveMocker = new BaseCommandMocker(client, targetDeviceCert, "auto-drive", 1000) {
//            @Override
//            public String mock(long sequence) {
//                return "{\"route\":\"\",\"speed\":\"\"}";
//            }
//        };
//
//        autoDriveMocker.start();
//        autoDriveMocker.join();
//
//        client.disconnect();
//        client.close();
    }

    public static void handleBindCommand(String body) throws InvalidProtocolBufferException {
        targetDeviceCert = CertificateProto.Certificate.parseFrom(ByteString.copyFromUtf8(body));
        latch.countDown();
    }

}
