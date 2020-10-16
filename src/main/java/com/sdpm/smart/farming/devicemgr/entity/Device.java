package com.sdpm.smart.farming.devicemgr.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 设备详情实体
 *
 * @author rukey
 */
@Entity
public class Device {
    /**
     * 设备ID 唯一主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备类型
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "device_type_id")
    private DeviceType type;

    /**
     * 设备描述
     */
    private String description;

    /**
     * 设备状态
     */
    private String status;

    /**
     * 设备IP
     */
    private String ipaddress;

    /**
     * 上线时间
     */
    private Long onlineTime;

    /**
     * 下线时间
     */
    private Long offlineTime;
    /**
     * 目标设备
     * 当绑定关系后，当前设备可以给目标设备发送信息
     */
    private Integer targetId;
    private Integer sourceId;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DeviceRecord> deviceRecords;

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

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public Long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Long onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Long getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Long offlineTime) {
        this.offlineTime = offlineTime;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public List<DeviceRecord> getDeviceRecords() {
        return deviceRecords;
    }

    public void setDeviceRecords(List<DeviceRecord> deviceRecords) {
        this.deviceRecords = deviceRecords;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", onlineTime=" + onlineTime +
                ", offlineTime=" + offlineTime +
                '}';
    }
}
