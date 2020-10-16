package com.sdpm.smart.farming.devicemgr.vo;

import com.sdpm.smart.farming.devicemgr.entity.DeviceEvent;
import org.springframework.beans.BeanUtils;


/**
 * @author shirukai
 */
public class DeviceEventVO {
    /**
     * 事件ID 唯一主键
     */
    private Integer id;

    /**
     * 设备VO
     */
    private DeviceVO device;

    /**
     * 事件类型ID
     */
    private DeviceEventTypeVO type;
    /**
     * 事件内容
     */
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

    public DeviceVO getDevice() {
        return device;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }

    public DeviceEventTypeVO getType() {
        return type;
    }

    public void setType(DeviceEventTypeVO type) {
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

    public static DeviceEventVO toVO(DeviceEvent deviceEvent, DeviceVO deviceVO, DeviceEventTypeVO typeVO) {
        DeviceEventVO vo = new DeviceEventVO();
        BeanUtils.copyProperties(deviceEvent, vo);
        vo.setDevice(deviceVO);
        vo.setType(typeVO);
        return vo;
    }

    @Override
    public String toString() {
        return "DeviceEventVO{" +
                "id=" + id +
                ", device=" + device.getId() +
                ", type=" + type.getType() +
                ", body='" + body + '\'' +
                ", created=" + created +
                '}';
    }
}
