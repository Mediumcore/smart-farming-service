package com.sdpm.smart.farming.mock;

import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import org.eclipse.paho.client.mqttv3.MqttClient;

import java.util.Random;

/**
 * 速度事件模拟器
 *
 * @author shirukai
 */
public class VelocityEventMocker extends BaseEventMocker {
    public VelocityEventMocker(MqttClient client, CertificateProto.Certificate certificate) {
        super(client, certificate, "velocity");

    }

    public VelocityEventMocker(MqttClient client, CertificateProto.Certificate certificate, String eventType, long interval) {
        super(client, certificate, eventType, interval);
    }

    @Override
    public String mock(long sequence) {
        // 0-220随机数
        return Integer.toString(new Random().nextInt(220));
    }
}
