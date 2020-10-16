package com.sdpm.smart.farming.devicemgr.vo;

import com.sdpm.smart.farming.devicemgr.entity.DeviceType;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author rukey
 */
public class DeviceTypeVO implements Serializable {

    private static final long serialVersionUID = 6982066497774654009L;
    /**
     * 设备类型ID 唯一主键
     */

    private Integer id;

    /**
     * 类型名称
     */
    private String type;

    /**
     * 类型标签
     */
    private String label;

    /**
     * 类型描述
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static DeviceTypeVO toVO(DeviceType deviceType) {
        DeviceTypeVO vo = new DeviceTypeVO();
        BeanUtils.copyProperties(deviceType, vo);
        return vo;
    }

    @Override
    public String toString() {
        return "DeviceTypeVO{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
