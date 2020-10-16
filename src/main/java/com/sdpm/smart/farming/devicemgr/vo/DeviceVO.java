package com.sdpm.smart.farming.devicemgr.vo;

import com.sdpm.smart.farming.devicemgr.entity.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Base64Utils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author rukey
 */
public class DeviceVO {
    /**
     * 设备ID 唯一主键
     */
    private Integer id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备类型
     */
    private DeviceTypeVO type;

    /**
     * 设备描述
     */
    private String description;

    /**
     * 设备IP
     */
    private String ipaddress;

    /**
     * 设备状态
     */
    private String status;

    /**
     * 上线时间
     */
    private Long onlineTime;

    /**
     * 下线时间
     */
    private Long offlineTime;

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

    public DeviceTypeVO getType() {
        return type;
    }

    public void setType(DeviceTypeVO type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "DeviceVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", status='" + status + '\'' +
                ", onlineTime=" + onlineTime +
                ", offlineTime=" + offlineTime +
                '}';
    }

    public static DeviceVO toVO(Device device) {
        DeviceVO vo = new DeviceVO();
        BeanUtils.copyProperties(device, vo);
        vo.setType(DeviceTypeVO.toVO(device.getType()));
        return vo;
    }
}
