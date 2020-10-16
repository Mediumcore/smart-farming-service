package com.sdpm.smart.farming.devicemgr.controller;

import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import com.sdpm.smart.farming.devicemgr.entity.DeviceType;
import com.sdpm.smart.farming.devicemgr.service.DeviceTypeService;
import com.sdpm.smart.farming.devicemgr.vo.DeviceEventTypeVO;
import com.sdpm.smart.farming.devicemgr.vo.DeviceTypeVO;
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
 * 设备类型管理API
 *
 * @author rukey
 */
@RestController
@RequestMapping("/api/v1/devicemgr/types")
@Api(tags = {"设备类型管理"})
public class DeviceTypeController {
    @Autowired
    private DeviceTypeService deviceTypeService;

    @ApiOperation(value = "获取设备类型列表", notes = "分页获取设备类型列表")
    @GetMapping
    public RestMessage types(
            @ApiParam("typeName") @RequestParam(value = "typeName", required = false) String typeName,
            @ApiParam("pageNum") @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam("pageSize") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<DeviceTypeVO> page = deviceTypeService.findDeviceTypesWithPage(typeName, pageable).map(this::toVO);
        return RestMessageUtil.pageToRestMessage(page);
    }

    @ApiOperation(value = "添加设备类型", notes = "添加设备类型")
    @PostMapping
    public RestMessage addCommandType(
            @ApiParam("type") @RequestParam(value = "type") String type,
            @ApiParam("label") @RequestParam(value = "label") String label,
            @ApiParam("description") @RequestParam(value = "description") String description
    ) {
        DeviceTypeVO deviceTypeVO = new DeviceTypeVO();
        deviceTypeVO.setType(type);
        deviceTypeVO.setLabel(label);
        deviceTypeVO.setDescription(description);
        return RestMessageUtil.objectToRestMessage(toVO(deviceTypeService.addDeviceType(deviceTypeVO)));
    }

    @ApiOperation(value = "修改设备类型", notes = "修改设备类型")
    @PutMapping
    public RestMessage updateCommandType(
            @ApiParam("type") @RequestParam(value = "typeId") String type,
            @ApiParam("label") @RequestParam(value = "label") String label,
            @ApiParam("description") @RequestParam(value = "description") String description
    ) {
        DeviceTypeVO deviceTypeVO = new DeviceTypeVO();
        deviceTypeVO.setType(type);
        deviceTypeVO.setLabel(label);
        deviceTypeVO.setDescription(description);
        return RestMessageUtil.objectToRestMessage(toVO(deviceTypeService.updateDeviceType(deviceTypeVO)));
    }

    @ApiOperation(value = "删除设备类型", notes = "删除设备类型")
    @RequestMapping(value = "/{type}", method = RequestMethod.DELETE)
    public RestMessage deleteCommandType(
            @ApiParam(value = "指令类型") @PathVariable("type") String type
    ) {
        deviceTypeService.removeDeviceType(type);
        return RestMessageUtil.objectToRestMessage("设备类型删除成功");
    }

    public DeviceTypeVO toVO(DeviceType deviceType) {
        return DeviceTypeVO.toVO(deviceType);
    }
}
