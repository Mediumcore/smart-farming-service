package com.sdpm.smart.farming.devicemgr.vo;

import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 设备事件类型VO
 *
 * @author rukey
 */
public class DeviceEventTypeVO implements Serializable {

    private static final long serialVersionUID = -6547238484026161410L;
    private Integer id;
    /**
     * 事件类型
     */
    private String type;
    /**
     * 事件标签
     */
    private String label;

    /**
     * 事件描述
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static DeviceEventTypeVO toVO(DeviceEventType deviceEventType) {
        DeviceEventTypeVO vo = new DeviceEventTypeVO();
        BeanUtils.copyProperties(deviceEventType, vo);
        return vo;
    }

    @Override
    public String toString() {
        return "DeviceEventTypeVO{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
