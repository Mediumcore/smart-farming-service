package com.sdpm.smart.farming.devicemgr.controller;

import com.alibaba.fastjson.JSONArray;
import com.sdpm.smart.farming.common.exception.SmartFarmingServiceException;
import com.sdpm.smart.farming.common.rest.RestMessage;
import com.sdpm.smart.farming.common.util.RestMessageUtil;
import com.sdpm.smart.farming.devicemgr.service.TraceRouteService;
import com.sdpm.smart.farming.devicemgr.vo.DeviceEventVO;
import com.sdpm.smart.farming.devicemgr.vo.TraceRouteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shirukai
 */
@RestController
@RequestMapping("/api/v1/devicemgr/traceroute")
@Api(tags = {"寻迹路线管理"})
public class TraceRouteController {
    @Autowired
    private TraceRouteService traceRouteService;

    @ApiOperation(value = "上传KML", notes = "上传Google地球的导出的KML文件")
    @PostMapping("/kml/upload")
    public RestMessage kmlUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String xmlContent = new String(file.getBytes(), StandardCharsets.UTF_8);
        return RestMessageUtil.objectToRestMessage(traceRouteService.kmlUpload(xmlContent));
    }

    @ApiOperation(value = "寻迹路线列表", notes = "分页获取寻迹路线列表")
    @GetMapping
    public RestMessage devices(
            @ApiParam("name") @RequestParam(value = "name", required = false) String name,
            @ApiParam("pageNum") @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @ApiParam("pageSize") @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<TraceRouteVO> page = traceRouteService.queryTraceRoute(name, pageable).map(TraceRouteVO::toVO);
        return RestMessageUtil.pageToRestMessage(page);
    }

    @ApiOperation(value = "删除寻迹路线", notes = "删除寻迹路线")
    @RequestMapping(value = "/{routeId}", method = RequestMethod.DELETE)
    public RestMessage deleteRoute(
            @ApiParam(value = "路线ID") @PathVariable("routeId") Integer routeId
    ) {
        traceRouteService.removeRoute(routeId);
        return RestMessageUtil.objectToRestMessage("路线删除成功");
    }

    @ApiOperation(value = "获取寻迹路线", notes = "获取寻迹路线")
    @RequestMapping(value = "/{routeId}", method = RequestMethod.GET)
    public RestMessage getRoute(
            @ApiParam(value = "路线ID") @PathVariable("routeId") Integer routeId
    ) {
        return RestMessageUtil.objectToRestMessage(traceRouteService.findTraceRoute(routeId));
    }


    @ApiOperation(value = "导出路线", notes = "导出路线")
    @RequestMapping(value = "/{routeId}/export", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> exportRoute(
            @ApiParam(value = "路线ID") @PathVariable("routeId") Integer routeId
    ) throws IOException {
        TraceRouteVO traceRouteVO = traceRouteService.findTraceRoute(routeId);
        File file = File.createTempFile(traceRouteVO.getName(), ".txt");
        List<String> lines =
                traceRouteVO.getLocations().stream()
                        .map(line -> String.join(" ", ((JSONArray) line).toJavaList(String.class)))
                        .collect(Collectors.toList());
        String lineString = String.join("\n", lines);
        IOUtils.write(lineString, new FileOutputStream(file), StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }

    @PostMapping(value = "/import")
    public RestMessage importRoute(
            @RequestParam("name") String routeName,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (StringUtils.isEmpty(routeName)) {
            throw new SmartFarmingServiceException("路线名称不能为空。");
        }
        String routeContent = new String(file.getBytes(), StandardCharsets.UTF_8);
        List<Object> lines = Arrays
                .stream(routeContent.split("\n"))
                .map(l -> Arrays.stream(l.split(" ")).map(Float::parseFloat).toArray())
                .collect(Collectors.toList());
        JSONArray locations = new JSONArray(lines);
        TraceRouteVO traceRouteVO = new TraceRouteVO();
        traceRouteVO.setName(routeName);
        traceRouteVO.setCenter(locations.getJSONArray(0));
        traceRouteVO.setLocations(locations);

        return RestMessageUtil.objectToRestMessage(traceRouteService.saveTraceRoute(traceRouteVO));
    }

    @ApiOperation(value = "保存路线", notes = "保存路线")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestMessage saveRoute(
            @RequestBody TraceRouteVO traceRouteVO
    ) {
        return RestMessageUtil.objectToRestMessage(traceRouteService.saveTraceRoute(traceRouteVO));
    }


}
