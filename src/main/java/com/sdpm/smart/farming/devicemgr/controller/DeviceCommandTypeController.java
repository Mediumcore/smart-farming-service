package com.sdpm.smart.farming.devicemgr.controller;

import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommandType;
import com.sdpm.smart.farming.devicemgr.service.DeviceCommandTypeService;
import com.sdpm.smart.farming.devicemgr.vo.DeviceCommandTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * 设备指令类型管理API
 *
 * @author rukey
 */
@RestController
@RequestMapping("/api/v1/devicemgr/command/types")
@Api(tags = {"指令类型管理"})
public class DeviceCommandTypeController {
    @Autowired
    private DeviceCommandTypeService deviceCommandTypeService;

    @ApiOperation(value = "获取指令类型列表", notes = "分页获取指令类型列表")
    @GetMapping
    public RestMessage commandTypes(
            @ApiParam("typeName") @RequestParam(value = "typeName", required = false) String typeName,
            @ApiParam("pageNum") @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam("pageSize") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<DeviceCommandTypeVO> page = deviceCommandTypeService.
                findDeviceCommandTypesWithPage(typeName, pageable).map(this::toVO);
        return RestMessageUtil.pageToRestMessage(page);
    }

    @ApiOperation(value = "添加指令类型", notes = "添加指令类型")
    @PostMapping
    public RestMessage addCommandType(
            @ApiParam("type") @RequestParam(value = "type") String type,
            @ApiParam("label") @RequestParam(value = "label") String label,
            @ApiParam("description") @RequestParam(value = "description") String description
    ) {
        DeviceCommandTypeVO commandTypeVO = new DeviceCommandTypeVO();
        commandTypeVO.setType(type);
        commandTypeVO.setLabel(label);
        commandTypeVO.setDescription(description);
        return RestMessageUtil.objectToRestMessage(toVO(deviceCommandTypeService.addDeviceCommandType(commandTypeVO)));
    }

    @ApiOperation(value = "修改指令类型", notes = "修改指令类型")
    @PutMapping
    public RestMessage updateCommandType(
            @ApiParam("type") @RequestParam(value = "typeId") String type,
            @ApiParam("label") @RequestParam(value = "label") String label,
            @ApiParam("description") @RequestParam(value = "description") String description
    ) {
        DeviceCommandTypeVO commandTypeVO = new DeviceCommandTypeVO();
        commandTypeVO.setType(type);
        commandTypeVO.setLabel(label);
        commandTypeVO.setDescription(description);
        return RestMessageUtil.objectToRestMessage(toVO(deviceCommandTypeService.updateDeviceCommandType(commandTypeVO)));
    }

    @ApiOperation(value = "删除指令类型", notes = "删除指令类型")
    @RequestMapping(value = "/{typeId}", method = RequestMethod.DELETE)
    public RestMessage deleteCommandType(
            @ApiParam(value = "指令类型ID") @PathVariable("typeId") Integer typeId
    ) {
        deviceCommandTypeService.removeDeviceCommandTypeId(typeId);
        return RestMessageUtil.objectToRestMessage("指令类型删除成功");
    }

    public DeviceCommandTypeVO toVO(DeviceCommandType commandType) {
        return DeviceCommandTypeVO.toVO(commandType);
    }
}
