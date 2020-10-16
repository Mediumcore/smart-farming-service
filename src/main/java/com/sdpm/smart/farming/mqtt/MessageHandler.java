package com.sdpm.smart.farming.mqtt;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sdpm.smart.farming.devicemgr.message.DeviceMessageProto;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * message handler
 *
 * @author rukey
 */
public abstract class MessageHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    private BlockingDeque<MessageWithTopic> messages = new LinkedBlockingDeque<>();
    private Boolean running;

    public MessageHandler() {
    }

    public final DeviceMessageProto.DeviceMessage deserialization(byte[] payload) throws InvalidProtocolBufferException {
        // 使用Protobuf进行反序列化
        return DeviceMessageProto.DeviceMessage.parseFrom(payload);
    }

    @Override
    public final void run() {
        this.running = true;
        while (running) {
            MessageWithTopic messageWithTopic;
            try {
                messageWithTopic = messages.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            String topic = messageWithTopic.getTopic();
            MqttMessage message = messageWithTopic.getMessage();
            try {
                // 反序列化
                DeviceMessageProto.DeviceMessage payload = deserialization(message.getPayload());
                this.handle(topic, payload);
            } catch (Exception e) {
                logger.error("Failed to handle message: {},error: {}", message.getId(), e.getMessage());
            }
        }

    }

    public final void put(MessageWithTopic message) {
        this.messages.add(message);
    }

    // handle message
    public void handle(String topic, DeviceMessageProto.DeviceMessage message) {
    }

    public final void stop() {
        this.running = false;
    }

}
