package com.sdpm.smart.farming.devicemgr.controller;


import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.entity.*;
import com.sdpm.smart.farming.devicemgr.service.*;
import com.sdpm.smart.farming.devicemgr.vo.DeviceCommandTypeVO;
import com.sdpm.smart.farming.devicemgr.vo.DeviceCommandVO;
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
import org.springframework.web.bind.annotation.*;


/**
 * 指令管理API
 *
 * @author rukey
 */
@RestController
@RequestMapping("/api/v1/devicemgr/commands")
@Api(tags = {"指令管理"})
public class DeviceCommandController {
    @Autowired
    private DeviceCommandService commandService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceCommandTypeService commandTypeService;

    @ApiOperation(value = "指令列表", notes = "分页获取指令列表")
    @GetMapping
    public RestMessage commands(
            @ApiParam("deviceId") @RequestParam(value = "deviceId", required = false, defaultValue = "0") Integer deviceId,
            @ApiParam("typeId") @RequestParam(value = "typeId", required = false, defaultValue = "0") Integer typeId,
            @ApiParam("startTime") @RequestParam(value = "startTime", required = false, defaultValue = "0") Long startTime,
            @ApiParam("endTime") @RequestParam(value = "endTime", required = false, defaultValue = "0") Long endTime,
            @ApiParam("pageNum") @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam("pageSize") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<DeviceCommandVO> page = commandService.queryDeviceCommand(
                deviceId, typeId, startTime, endTime, pageable
        ).map(this::toVO);
        return RestMessageUtil.pageToRestMessage(page);
    }


    public DeviceCommandVO toVO(DeviceCommand command) {

        Device device = deviceService.findDeviceById(command.getDeviceId());
        DeviceCommandType commandType = commandTypeService.findCommandTypeById(command.getTypeId());
        //todo  body 转 json
        return DeviceCommandVO.toVO(command, DeviceVO.toVO(device), DeviceCommandTypeVO.toVO(commandType));
    }
}
