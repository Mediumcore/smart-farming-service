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
public abstract class BaseEventMocker extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(BaseEventMocker.class);
    private boolean running = false;
    private long interval;
    private final MqttClient client;
    private final String eventType;
    private final CertificateProto.Certificate certificate;

    public BaseEventMocker(MqttClient client, CertificateProto.Certificate certificate, String eventType) {
        this(client, certificate, eventType, 1000);
    }

    public BaseEventMocker(MqttClient client, CertificateProto.Certificate certificate, String eventType, long interval) {
        this.certificate = certificate;
        this.interval = interval;
        this.eventType = eventType;
        this.client = client;
    }

    public BaseEventMocker setInterval(long interval) {
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
                        .setType(eventType)
                        .build();
                MqttMessage mqttMessage = new MqttMessage();
                mqttMessage.setQos(certificate.getQos());
                mqttMessage.setPayload(eventMessage.toByteArray());
                // 模拟发送事件
                client.publish(certificate.getEventTopic(), mqttMessage);
                logger.info("Send event to smart farming service.type={},body={},created={}", eventType, body, created);
                Thread.sleep(interval);
                sequence++;
            } catch (UnsupportedEncodingException | MqttException | InterruptedException e) {
                logger.error("Mock {} event failed!", eventType, e);
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
