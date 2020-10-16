package com.sdpm.smart.farming.mock;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 模拟机械设备
 *
 * @author rukey
 */
public class MockDevice {
    private static final Logger logger = LoggerFactory.getLogger(MockDevice.class);

    public static void main(String[] args) throws MqttException, InvalidProtocolBufferException, InterruptedException {
        List<MqttClient> clients = Stream.of(
                "CAESGHRjcDovLzQ3LjExNC4zOS4xNDI6MTg4MxgBIgllbXF4MTIzNCEqBGVtcXgyEnNmcy1kZXZpY2UtMTAwMDItMToSc2ZzLzEwMDAyL2V2ZW50cy8xQhRzZnMvMTAwMDIvY29tbWFuZHMvMUoVc2ZzLXJ0Yy12aWRlby0xMDAwMi0xUhlzZnMvMTAwMDIvcnRjL3ZpZGVvL3JlcS8xWhlzZnMvMTAwMDIvcnRjL3ZpZGVvL2Fucy8xYhRzZnMtcnRjLWRhdGEtMTAwMDItMWoYc2ZzLzEwMDAyL3J0Yy9kYXRhL3JlcS8xchhzZnMvMTAwMDIvcnRjL2RhdGEvYW5zLzF6F3R1cm46NDcuMTE0LjM5LjE0MjozNDc4ggEHa3VyZW50b4oBDGt1cmVudG8hMTIzNA==",
                "CAISGHRjcDovLzQ3LjExNC4zOS4xNDI6MTg4MxgBIgllbXF4MTIzNCEqBGVtcXgyEnNmcy1kZXZpY2UtMTAwMDItMjoSc2ZzLzEwMDAyL2V2ZW50cy8yQhRzZnMvMTAwMDIvY29tbWFuZHMvMkoVc2ZzLXJ0Yy12aWRlby0xMDAwMi0yUhlzZnMvMTAwMDIvcnRjL3ZpZGVvL3JlcS8yWhlzZnMvMTAwMDIvcnRjL3ZpZGVvL2Fucy8yYhRzZnMtcnRjLWRhdGEtMTAwMDItMmoYc2ZzLzEwMDAyL3J0Yy9kYXRhL3JlcS8ychhzZnMvMTAwMDIvcnRjL2RhdGEvYW5zLzJ6F3R1cm46NDcuMTE0LjM5LjE0MjozNDc4ggEHa3VyZW50b4oBDGt1cmVudG8hMTIzNA==",
                "CAMSGHRjcDovLzQ3LjExNC4zOS4xNDI6MTg4MxgBIgllbXF4MTIzNCEqBGVtcXgyEnNmcy1kZXZpY2UtMTAwMDItMzoSc2ZzLzEwMDAyL2V2ZW50cy8zQhRzZnMvMTAwMDIvY29tbWFuZHMvM0oVc2ZzLXJ0Yy12aWRlby0xMDAwMi0zUhlzZnMvMTAwMDIvcnRjL3ZpZGVvL3JlcS8zWhlzZnMvMTAwMDIvcnRjL3ZpZGVvL2Fucy8zYhRzZnMtcnRjLWRhdGEtMTAwMDItM2oYc2ZzLzEwMDAyL3J0Yy9kYXRhL3JlcS8zchhzZnMvMTAwMDIvcnRjL2RhdGEvYW5zLzN6F3R1cm46NDcuMTE0LjM5LjE0MjozNDc4ggEHa3VyZW50b4oBDGt1cmVudG8hMTIzNA=="
        ).map(s -> {
            try {
                CertificateProto.Certificate certificate = CertificateProto
                        .Certificate.parseFrom(Base64Utils.decodeFromString(s));
                String mqttUrl = certificate.getUrl();
                String username = certificate.getUsername();
                String password = certificate.getPassword();
                String clientId = certificate.getClientId();
                Thread.sleep(10000);
                return MQTTClientUtil.createClient(mqttUrl, clientId, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());

        Thread.sleep(20000);

        // 断开连接
        for (MqttClient client : clients) {
            client.disconnect();
            client.close();
            Thread.sleep(10000);
        }


    }

}
