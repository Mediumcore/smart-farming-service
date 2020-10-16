package com.sdpm.smart.farming.devicemgr.vo;


/**
 * @author shirukai
 */
public class LastDeviceEventVO {
    /**
     * 事件ID 唯一主键
     */
    private Integer id;

    /**
     * 设备ID
     */
    private Integer deviceId;

    /**
     * 事件类型ID
     */
    private String type;
    /**
     * 事件内容
     */
    private String body;
    /**
     * 事件产生时间
     */

    private Long created;

    public LastDeviceEventVO(Integer id, Integer deviceId, String type, String body, Long created) {
        this.id = id;
        this.deviceId = deviceId;
        this.type = type;
        this.body = body;
        this.created = created;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "LastDeviceEventVO{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", type='" + type + '\'' +
                ", body='" + body + '\'' +
                ", created=" + created +
                '}';
    }


}
