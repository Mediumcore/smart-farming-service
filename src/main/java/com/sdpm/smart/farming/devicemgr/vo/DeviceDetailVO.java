package com.sdpm.smart.farming.devicemgr.vo;

/**
 * 设备详情VO
 *
 * @author shirukai
 */
public class DeviceDetailVO {
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

    private Integer source;

    private Integer target;

    /**
     * 最新的控制状态
     */
    private String controlState;

    /**
     * 最新的位置信息
     */
    private String location;

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

    public String getControlState() {
        return controlState;
    }

    public void setControlState(String controlState) {
        this.controlState = controlState;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }
}
