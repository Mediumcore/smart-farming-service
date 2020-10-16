package com.sdpm.smart.farming.devicemgr.service;

import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import com.sdpm.smart.farming.devicemgr.entity.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

/**
 * @author shirukai
 */
@Service
public class OperatingService {
    private static final Logger logger = LoggerFactory.getLogger(OperatingService.class);
    private static final String BINDS_COMMAND_TYPE = "binds";
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceCommandService commandService;
    @Autowired
    private DeviceCommandTypeService commandTypeService;

    /**
     * 下发绑定指令到设备
     *
     * @param device 设备实例
     */
    @Async
    public void sendBindingCommand(Device device) {
        Integer targetId = device.getTargetId();
        if (null != targetId) {
            // 获取绑定信息
            CertificateProto.Certificate certificate = deviceService.getDeviceCertificate(targetId);
            commandService.sendCommand(BINDS_COMMAND_TYPE, Base64Utils.encodeToString(certificate.toByteArray()), device.getId());
        } else {
            logger.info("The current device is not bound to the target device,nothing to do.");
        }
    }


    /**
     * 创建绑定
     *
     * @param sourceId 设备ID
     * @param targetId 绑定目标设备的ID
     */
    public void createBinding(Integer sourceId, Integer targetId) {
        Device sourceDevice = deviceService.findDeviceById(sourceId);
        Device targetDevice = deviceService.findDeviceById(targetId);
        sourceDevice.setTargetId(targetId);
        targetDevice.setSourceId(sourceId);
        deviceService.saveDevice(sourceDevice);
        deviceService.saveDevice(targetDevice);
        sendBindingCommand(sourceDevice);
    }

    /**
     * 解除绑定
     *
     * @param sourceId 设备ID
     * @param targetId 解除绑定目标的设备ID
     */
    public void deleteBinding(Integer sourceId, Integer targetId) {
        Device device = deviceService.findDeviceById(sourceId);
        Device target = deviceService.findDeviceById(targetId);
        device.setTargetId(null);
        target.setSourceId(null);
        deviceService.saveDevice(device);
        deviceService.saveDevice(target);
        sendBindingCommand(device);
    }
}
