package com.sdpm.smart.farming.devicemgr.entity;


import io.swagger.models.auth.In;

import javax.persistence.*;
import java.util.Arrays;

/**
 * 设备指令实体
 *
 * @author rukey
 */
@Entity
public class DeviceCommand {
    /**
     * 指令ID 唯一主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备ID
     */
    private Integer deviceId;

    /**
     * 指令类型ID
     */
    private Integer typeId;
    /**
     * 指令内容
     */
    @Lob
    @Column(columnDefinition="TEXT")
    private String body;
    /**
     * 指令产生时间
     */
    private Long created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "DeviceCommand{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", typeId=" + typeId +
                ", body=" + body +
                ", created=" + created +
                '}';
    }
}
