package com.sdpm.smart.farming.devicemgr.vo;

import com.sdpm.smart.farming.devicemgr.entity.DeviceCommandType;
import org.springframework.beans.BeanUtils;

/**
 * 设备指令类型VO
 *
 * @author rukey
 */
public class DeviceCommandTypeVO {
    private Integer id;
    /**
     * 指令类型
     */
    private String type;
    /**
     * 指令标签
     */
    private String label;

    /**
     * 指令描述
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

    public static DeviceCommandTypeVO toVO(DeviceCommandType commandType) {
        DeviceCommandTypeVO vo = new DeviceCommandTypeVO();
        BeanUtils.copyProperties(commandType, vo);
        return vo;
    }

    @Override
    public String toString() {
        return "DeviceCommandTypeVO{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
