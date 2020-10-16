package com.sdpm.smart.farming.mock;

import com.google.protobuf.ByteString;
import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import com.sdpm.smart.farming.devicemgr.message.DeviceMessageProto;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author shirukai
 */
public abstract class BaseCommandMocker extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(BaseCommandMocker.class);
    private boolean running = false;
    private long interval;
    private final MqttClient client;
    private final String commandType;
    private final CertificateProto.Certificate certificate;

    public BaseCommandMocker(MqttClient client, CertificateProto.Certificate certificate, String commandType) {
        this(client, certificate, commandType, 1000);
    }

    public BaseCommandMocker(MqttClient client, CertificateProto.Certificate certificate, String commandType, long interval) {
        this.certificate = certificate;
        this.interval = interval;
        this.commandType = commandType;
        this.client = client;
    }

    public BaseCommandMocker setInterval(long interval) {
        this.interval = interval;
        return this;
    }

    @Override
    public void run() {
        this.running = true;
        long sequence = 1;
        while (running) {
            String body = this.mock(sequence);
            long created = System.currentTimeMillis();
            try {
                // 构建消息体
                DeviceMessageProto.DeviceMessage eventMessage = DeviceMessageProto.DeviceMessage
                        .newBuilder()
                        .setCreated(created)
                        .setBody(ByteString.copyFrom(body, "utf-8"))
                        .setType(commandType)
                        .build();
                MqttMessage mqttMessage = new MqttMessage();
                mqttMessage.setQos(certificate.getQos());
                mqttMessage.setPayload(eventMessage.toByteArray());
                // 模拟发送事件
                client.publish(certificate.getCommandTopic(), mqttMessage);
                logger.info("Send command to device {}.type={},body={},created={}", certificate.getClientId(), commandType, body, created);
                Thread.sleep(interval);
                sequence++;
            } catch (UnsupportedEncodingException | MqttException | InterruptedException e) {
                logger.error("Mock {} command failed!", commandType, e);
            }
        }
    }

    public String mock(long sequence) {
        return "";
    }

    public void cancel() {
        this.running = false;
    }
}
