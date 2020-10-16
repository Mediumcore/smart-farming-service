package com.sdpm.smart.farming.devicemgr.controller;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.certificate.CertificateProto;
import com.sdpm.smart.farming.devicemgr.entity.Device;
import com.sdpm.smart.farming.devicemgr.service.DeviceService;
import com.sdpm.smart.farming.devicemgr.vo.CertificateVO;
import com.sdpm.smart.farming.devicemgr.vo.DeviceRecordVO;
import com.sdpm.smart.farming.devicemgr.vo.DeviceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;


/**
 * 设备管理API
 *
 * @author rukey
 */
@RestController
@RequestMapping("/api/v1/devicemgr/devices")
@Api(tags = {"设备管理"})
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @ApiOperation(value = "获取设备列表", notes = "分页获取设备列表")
    @GetMapping
    public RestMessage devices(
            @ApiParam("deviceName") @RequestParam(value = "deviceName", required = false) String deviceName,
            @ApiParam("deviceType") @RequestParam(value = "deviceType", required = false) String deviceType,
            @ApiParam("pageNum") @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam("pageSize") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "onlineTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<DeviceVO> page = deviceService.findDevicesWithPage(deviceName, deviceType, pageable).map(this::toVO);
        return RestMessageUtil.pageToRestMessage(page);
    }

    @ApiOperation(value = "添加设备", notes = "添加设备")
    @PostMapping
    public RestMessage addDevice(
            @ApiParam("name") @RequestParam(value = "name") String name,
            @ApiParam("description") @RequestParam(value = "description") String description,
            @ApiParam("type") @RequestParam(value = "type") String type
    ) {
        return RestMessageUtil.objectToRestMessage(toVO(deviceService.addDevice(name, description, type)));
    }

    @ApiOperation(value = "修改设备", notes = "修改设备")
    @RequestMapping(value = "/{deviceId}", method = RequestMethod.PUT)
    public RestMessage updateDevice(
            @ApiParam(value = "设备ID") @PathVariable("deviceId") Integer deviceId,
            @ApiParam("name") @RequestParam(value = "name") String name,
            @ApiParam("description") @RequestParam(value = "description") String description
    ) {
        return RestMessageUtil.objectToRestMessage(toVO(deviceService.updateDevice(deviceId, name, description)));
    }

    @ApiOperation(value = "删除设备", notes = "删除设备")
    @RequestMapping(value = "/{deviceId}", method = RequestMethod.DELETE)
    public RestMessage deleteCommandType(
            @ApiParam(value = "设备ID") @PathVariable("deviceId") Integer deviceId
    ) {
        deviceService.removeDevice(deviceId);
        return RestMessageUtil.objectToRestMessage("设备删除成功");
    }

    @ApiOperation(value = "获取设备接入凭证", notes = "获取设备接入凭证")
    @RequestMapping(value = "/{deviceId}/certificate", method = RequestMethod.GET)
    public RestMessage deviceCertificate(
            @ApiParam(value = "设备ID") @PathVariable("deviceId") Integer deviceId
    ) throws InvalidProtocolBufferException {
        CertificateProto.Certificate certificate = deviceService.getDeviceCertificate(deviceId);
        Object plaintext = JSON.parse(JsonFormat.printer().print(certificate));
        String ciphertext = Base64Utils.encodeToString(certificate.toByteArray());
        CertificateVO certificateVO = new CertificateVO();
//        certificateVO.setPlaintext(plaintext);
        certificateVO.setCiphertext(ciphertext);
        return RestMessageUtil.objectToRestMessage(certificateVO);
    }

    @ApiOperation(value = "获取指定设备上下线记录", notes = "获取设备上下线记录")
    @RequestMapping(value = "/{deviceId}/records", method = RequestMethod.GET)
    public RestMessage deviceRecordsByDevice(
            @ApiParam(value = "设备ID") @PathVariable("deviceId") Integer deviceId,
            @ApiParam("timeType") @RequestParam(value = "timeType", required = false, defaultValue = "onlineTime") String timeType,
            @ApiParam("startTime") @RequestParam(value = "startTime", required = false, defaultValue = "0") Long startTime,
            @ApiParam("endTime") @RequestParam(value = "endTime", required = false, defaultValue = "0") Long endTime,
            @ApiParam("pageNum") @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam("pageSize") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "onlineTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<DeviceRecordVO> recordVOS = deviceService.findDeviceRecords(deviceId, timeType, startTime, endTime, pageable).map(deviceRecord -> {
            DeviceRecordVO recordVO = new DeviceRecordVO();
            BeanUtils.copyProperties(deviceRecord, recordVO);
            return recordVO;
        });
        return RestMessageUtil.pageToRestMessage(recordVOS);
    }

    @ApiOperation(value = "获取设备上下线记录", notes = "获取设备上下线记录")
    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public RestMessage deviceRecords(
            @ApiParam("timeType") @RequestParam(value = "timeType", required = false, defaultValue = "onlineTime") String timeType,
            @ApiParam("startTime") @RequestParam(value = "startTime", required = false, defaultValue = "0") Long startTime,
            @ApiParam("endTime") @RequestParam(value = "endTime", required = false, defaultValue = "0") Long endTime,
            @ApiParam("pageNum") @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam("pageSize") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "onlineTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<DeviceRecordVO> recordVOS = deviceService.findDeviceRecords(null, timeType, startTime, endTime, pageable).map(DeviceRecordVO::toVO);
        return RestMessageUtil.pageToRestMessage(recordVOS);
    }


    public DeviceVO toVO(Device device) {
        return DeviceVO.toVO(device);
    }
}
