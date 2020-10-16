package com.sdpm.smart.farming.devicemgr.entity;

import javax.persistence.*;

/**
 * 设备上下线记录
 *
 * @author shirukai
 */
@Entity
public class DeviceRecord {
    /**
     * 设备记录ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "device_id")
    private Device device;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "DeviceRecord{" +
                "id=" + id +
                ", ipaddress='" + ipaddress + '\'' +
                ", onlineTime=" + onlineTime +
                ", offlineTime=" + offlineTime +
                ", deviceId=" + device.getId() +
                '}';
    }
}
