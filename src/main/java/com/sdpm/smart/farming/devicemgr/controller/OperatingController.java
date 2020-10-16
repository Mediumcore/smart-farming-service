package com.sdpm.smart.farming.devicemgr.controller;

import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.entity.Device;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEvent;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import com.sdpm.smart.farming.devicemgr.service.*;
import com.sdpm.smart.farming.devicemgr.vo.DeviceDetailVO;
import com.sdpm.smart.farming.devicemgr.vo.DeviceEventVO;
import com.sdpm.smart.farming.devicemgr.vo.LastDeviceEventVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 操作平台控制层
 *
 * @author shirukai
 */

@RestController
@Api(tags = {"设备操作"})
@RequestMapping("/api/v1/operating")
public class OperatingController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceEventService eventService;
    @Autowired
    private DeviceEventTypeService eventTypeService;
    @Autowired
    private OperatingService operatingService;
    @Autowired
    private DeviceCommandService commandService;

    @GetMapping("/device/{deviceId}/detail")
    @ApiOperation(value = "获取设备详情", notes = "获取设备详情")
    public RestMessage deviceDetail(
            @PathVariable("deviceId") Integer deviceId
    ) {
        DeviceEventType locationType = eventTypeService.findEventTypeByType("location");
        DeviceEventType controlStateType = eventTypeService.findEventTypeByType("control-state");
        Device device = deviceService.findDeviceById(deviceId);
        DeviceEvent lastLocationEvent = eventService.getLastEventByTypeIdAndDeviceId(locationType.getId(), deviceId);
        DeviceEvent lastControlStateEvent = eventService.getLastEventByTypeIdAndDeviceId(controlStateType.getId(), deviceId);
        DeviceDetailVO detailVO = new DeviceDetailVO();
        BeanUtils.copyProperties(device, detailVO);
        if (lastControlStateEvent != null) {
            detailVO.setControlState(lastControlStateEvent.getBody());
        }
        if (lastLocationEvent != null) {
            detailVO.setLocation(lastLocationEvent.getBody());
        }

        return RestMessageUtil.objectToRestMessage(detailVO);
    }

    @PostMapping("/device/{deviceId}/binds")
    @ApiOperation(value = "设备绑定", notes = "设备绑定")
    public RestMessage createBinds(
            @PathVariable("deviceId") Integer deviceId,
            @RequestParam("targetId") Integer targetId
    ) {
        operatingService.createBinding(deviceId, targetId);
        return RestMessageUtil.objectToRestMessage("绑定成功。");
    }

    @DeleteMapping("/device/{deviceId}/binds")
    @ApiOperation(value = "设备解除绑定", notes = "设备解除绑定")
    public RestMessage deleteBinds(
            @PathVariable("deviceId") Integer deviceId,
            @RequestParam("targetId") Integer targetId
    ) {
        operatingService.deleteBinding(deviceId, targetId);
        return RestMessageUtil.objectToRestMessage("解绑成功。");
    }

    @PostMapping("/command/{targetId}")
    @ApiOperation(value = "向设备发送指令", notes = "向设备发送指令")
    public RestMessage sendCommand(
            @PathVariable("targetId") Integer targetId,
            @RequestParam("type") String type,
            @RequestParam("body") String body
    ) {
        commandService.sendCommand(type, body, targetId);
        return RestMessageUtil.objectToRestMessage("发送成功。");
    }


    @GetMapping("/device/{deviceId}/event/{eventType}/last")
    @ApiOperation(value = "获取设备某个事件类型的最新事件", notes = "获取设备某个事件类型的最新事件")
    public RestMessage deviceDetail(
            @PathVariable("deviceId") Integer deviceId,
            @PathVariable("eventType") String type
    ) {
        DeviceEventType eventType = eventTypeService.findEventTypeByType(type);
        DeviceEvent lastEvent = eventService.getLastEventByTypeIdAndDeviceId(eventType.getId(), deviceId);
        if (lastEvent == null) {
            throw new RuntimeException("没有获取最新的事件。");
        }
        LastDeviceEventVO lastDeviceEventVO = new LastDeviceEventVO(lastEvent.getId(), deviceId, type, lastEvent.getBody(), lastEvent.getCreated());
        return RestMessageUtil.objectToRestMessage(lastDeviceEventVO);
    }


}
