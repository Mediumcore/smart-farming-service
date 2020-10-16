package com.sdpm.smart.farming.devicemgr.controller;

import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.entity.DeviceType;
import com.sdpm.smart.farming.devicemgr.service.DeviceCommandService;
import com.sdpm.smart.farming.devicemgr.service.DeviceEventService;
import com.sdpm.smart.farming.devicemgr.service.DeviceService;
import com.sdpm.smart.farming.devicemgr.service.DeviceTypeService;
import com.sdpm.smart.farming.devicemgr.vo.OverviewVO;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 指令管理API
 *
 * @author rukey
 */
@RestController
@RequestMapping("/api/v1/devicemgr/overview")
@Api(tags = {"概览"})
public class OverviewController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceTypeService deviceTypeService;
    @Autowired
    private DeviceEventService eventService;
    @Autowired
    private DeviceCommandService commandService;

    @GetMapping
    public RestMessage overview() {
        String currentDate = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        Map<String, OverviewVO> overviews = new HashMap<>(4);
        // 获取农机，控制器在线占比
        List<String> types = Arrays.asList("machinery", "controller");
        types.forEach(typeName -> {
            OverviewVO overview = new OverviewVO();
            overview.setType(typeName);
            DeviceType type = deviceTypeService.findDeviceTypeByType(typeName);
            overview.setValue(deviceService.onlineProportionByType(type.getId()));
            overview.setDate(currentDate);
            overviews.put(typeName, overview);

        });

        // 获取事件数量
        OverviewVO eventOverview = new OverviewVO();
        eventOverview.setDate(currentDate);
        eventOverview.setType("event");
        eventOverview.setValue(eventService.eventCount());
        overviews.put("event", eventOverview);

        // 获取设备数量
        OverviewVO commandOverview = new OverviewVO();
        commandOverview.setDate(currentDate);
        commandOverview.setType("command");
        commandOverview.setValue(commandService.commandCount());
        overviews.put("command", commandOverview);

        return RestMessageUtil.objectToRestMessage(overviews);
    }
}
