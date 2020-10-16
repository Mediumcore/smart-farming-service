package com.sdpm.smart.farming.devicemgr.controller;

import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.entity.Device;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEvent;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import com.sdpm.smart.farming.devicemgr.service.DeviceEventService;
import com.sdpm.smart.farming.devicemgr.service.DeviceEventTypeService;
import com.sdpm.smart.farming.devicemgr.service.DeviceService;
import com.sdpm.smart.farming.devicemgr.vo.DeviceEventTypeVO;
import com.sdpm.smart.farming.devicemgr.vo.DeviceEventVO;
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
 * 事件管理API
 *
 * @author rukey
 */
@RestController
@RequestMapping("/api/v1/devicemgr/events")
@Api(tags = {"事件管理"})
public class DeviceEventController {
    @Autowired
    private DeviceEventService eventService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceEventTypeService eventTypeService;

    @ApiOperation(value = "事件列表", notes = "分页获取事件列表")
    @GetMapping
    public RestMessage devices(
            @ApiParam("deviceId") @RequestParam(value = "deviceId", required = false, defaultValue = "0") Integer deviceId,
            @ApiParam("typeId") @RequestParam(value = "typeId", required = false, defaultValue = "0") Integer typeId,
            @ApiParam("startTime") @RequestParam(value = "startTime", required = false, defaultValue = "0") Long startTime,
            @ApiParam("endTime") @RequestParam(value = "endTime", required = false, defaultValue = "0") Long endTime,
            @ApiParam("pageNum") @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam("pageSize") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        Page<DeviceEventVO> page = eventService.queryDeviceEvent(
                deviceId, typeId, startTime, endTime, pageable
        ).map(this::toVO);
        return RestMessageUtil.pageToRestMessage(page);
    }


    public DeviceEventVO toVO(DeviceEvent event) {

        Device device = deviceService.findDeviceById(event.getDeviceId());
        DeviceEventType eventType = eventTypeService.findEventTypeById(event.getTypeId());
        //todo  body 转 json
        return DeviceEventVO.toVO(event, DeviceVO.toVO(device), DeviceEventTypeVO.toVO(eventType));
    }
}
