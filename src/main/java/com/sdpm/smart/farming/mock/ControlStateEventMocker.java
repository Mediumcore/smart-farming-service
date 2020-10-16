package com.sdpm.smart.farming.mock;

import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import org.eclipse.paho.client.mqttv3.MqttClient;

import java.util.Random;

/**
 * 控制状态事件模拟器
 *
 * @author shirukai
 */
public class ControlStateEventMocker extends BaseEventMocker {
    private static final String[] states = {"standby", "operating", "tracking"};

    public ControlStateEventMocker(MqttClient client, CertificateProto.Certificate certificate) {
        super(client, certificate, "control-state");
    }

    public ControlStateEventMocker(MqttClient client, CertificateProto.Certificate certificate, String eventType, long interval) {
        super(client, certificate, eventType, interval);
    }

    @Override
    public String mock(long sequence) {
        return states[new Random().nextInt(3)];
    }
}
