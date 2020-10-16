package com.sdpm.smart.farming.devicemgr.entity;



import javax.persistence.*;

/**
 * 设备实时事件实例
 *
 * @author rukey
 */
@Entity
public class DeviceEvent {
    /**
     * 事件ID 唯一主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 设备ID
     */
    private Integer deviceId;

    /**
     * 事件类型ID
     */
    private Integer typeId;
    /**
     * 事件内容
     */
    @Lob
    @Column(columnDefinition="TEXT")
    private String body;
    /**
     * 事件产生时间
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
        return "DeviceEvent{" +
                "id=" + id +
                ", deviceId=" + deviceId +
                ", typeId=" + typeId +
                ", body=" + body +
                ", created=" + created +
                '}';
    }
}
