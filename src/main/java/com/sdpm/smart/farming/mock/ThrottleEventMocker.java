package com.sdpm.smart.farming.mock;

import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import org.eclipse.paho.client.mqttv3.MqttClient;

import java.util.Random;

/**
 * 油门量事件模拟器
 *
 * @author shirukai
 */
public class ThrottleEventMocker extends BaseEventMocker {
    public ThrottleEventMocker(MqttClient client, CertificateProto.Certificate certificate) {
        super(client, certificate, "throttle");
    }

    public ThrottleEventMocker(MqttClient client, CertificateProto.Certificate certificate, String eventType, long interval) {
        super(client, certificate, eventType, interval);
    }

    @Override
    public String mock(long sequence) {
        // 0-100随机数
        return Integer.toString(new Random().nextInt(100));
    }
}
