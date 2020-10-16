package com.sdpm.smart.farming.devicemgr.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 设备事件类型
 *
 * @author rukey
 */
@Entity
public class DeviceEventType implements Serializable {

    private static final long serialVersionUID = 3292923161319987561L;
    /**
     * 设备事件类型ID 唯一主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public String toString() {
        return "DeviceEventType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
