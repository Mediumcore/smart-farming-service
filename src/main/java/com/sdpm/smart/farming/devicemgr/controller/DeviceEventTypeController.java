package com.sdpm.smart.farming.devicemgr.controller;

import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import com.sdpm.smart.farming.devicemgr.service.DeviceEventTypeService;
import com.sdpm.smart.farming.devicemgr.vo.DeviceEventTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 设备事件类型管理API
 *
 * @author rukey
 */
@RestController
@RequestMapping("/api/v1/devicemgr/event/types")
@Api(tags = {"事件类型管理"})
public class DeviceEventTypeController {
    @Autowired
    private DeviceEventTypeService deviceEventTypeService;

    @ApiOperation(value = "获取事件类型列表", notes = "分页获取事件类型列表")
    @GetMapping
    public RestMessage eventTypes(
            @ApiParam("typeName") @RequestParam(value = "typeName", required = false) String typeName,
            @ApiParam("pageNum") @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam("pageSize") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<DeviceEventTypeVO> page = deviceEventTypeService.
                findDeviceEventTypesWithPage(typeName, pageable)
                .map(this::toVO);
        return RestMessageUtil.pageToRestMessage(page);
    }

    @ApiOperation(value = "添加事件类型", notes = "添加事件类型")
    @PostMapping
    public RestMessage addCommandType(
            @ApiParam("type") @RequestParam(value = "type") String type,
            @ApiParam("label") @RequestParam(value = "label") String label,
            @ApiParam("description") @RequestParam(value = "description") String description
    ) {
        DeviceEventTypeVO eventTypeVO = new DeviceEventTypeVO();
        eventTypeVO.setType(type);
        eventTypeVO.setLabel(label);
        eventTypeVO.setDescription(description);
        DeviceEventType deviceEventType = deviceEventTypeService.addDeviceEventType(eventTypeVO);
        return RestMessageUtil.objectToRestMessage(toVO(deviceEventType));
    }

    @ApiOperation(value = "修改事件类型", notes = "修改事件类型")
    @PutMapping
    public RestMessage updateCommandType(
            @ApiParam("type") @RequestParam(value = "typeId") String type,
            @ApiParam("label") @RequestParam(value = "label") String label,
            @ApiParam("description") @RequestParam(value = "description") String description
    ) {
        DeviceEventTypeVO eventTypeVO = new DeviceEventTypeVO();
        eventTypeVO.setType(type);
        eventTypeVO.setLabel(label);
        eventTypeVO.setDescription(description);
        DeviceEventType deviceEventType = deviceEventTypeService.updateDeviceEventType(eventTypeVO);
        return RestMessageUtil.objectToRestMessage(toVO(deviceEventType));
    }

    @ApiOperation(value = "删除事件类型", notes = "删除事件类型")
    @RequestMapping(value = "/{typeId}", method = RequestMethod.DELETE)
    public RestMessage deleteCommandType(
            @ApiParam(value = "事件类型") @PathVariable("typeId") Integer typeId
    ) {
        deviceEventTypeService.removeDeviceEventTypeId(typeId);
        return RestMessageUtil.objectToRestMessage("事件类型删除成功");
    }

    public DeviceEventTypeVO toVO(DeviceEventType eventType) {
        return DeviceEventTypeVO.toVO(eventType);
    }
}
