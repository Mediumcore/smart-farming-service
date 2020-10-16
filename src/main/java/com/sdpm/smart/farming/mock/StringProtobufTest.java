package com.sdpm.smart.farming.mock;

import com.google.protobuf.ByteString;
import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import com.sdpm.smart.farming.devicemgr.message.DeviceMessageProto;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author shirukai
 */
public class StringProtobufTest {
    public static void main(String[] args) {
        DeviceMessageProto.DeviceMessage message1 = DeviceMessageProto.DeviceMessage.newBuilder().build();
        System.out.println(message1.toByteArray().length);
        String s = RandomStringUtils.random(16*1024);
        System.out.println(s.getBytes().length);
        DeviceMessageProto.DeviceMessage message2 = DeviceMessageProto.DeviceMessage.newBuilder()
                .setBody(ByteString.copyFromUtf8(s))
                .build();
        System.out.println(message2.toByteArray().length);

    }
}
