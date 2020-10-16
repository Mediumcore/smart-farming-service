package com.sdpm.smart.farming.devicemgr.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdpm.smart.farming.devicemgr.entity.TraceRoute;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 寻迹路线
 *
 * @author shirukai
 */
public class TraceRouteVO {
    /**
     * 路线ID 唯一主键
     */
    private Integer id;

    /**
     * 路线名称
     */
    private String name;

    private JSONArray center;

    private JSONArray locations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONArray getCenter() {
        return center;
    }

    public void setCenter(JSONArray center) {
        this.center = center;
    }

    public JSONArray getLocations() {
        return locations;
    }

    public void setLocations(JSONArray locations) {
        this.locations = locations;
    }

    public static TraceRouteVO toVO(TraceRoute traceRoute) {
        TraceRouteVO vo = new TraceRouteVO();
        vo.setId(traceRoute.getId());
        vo.setName(traceRoute.getName());
        vo.setCenter(JSON.parseArray(traceRoute.getCenter()));
        vo.setLocations(JSON.parseArray(traceRoute.getLocations()));
        return vo;
    }
}
