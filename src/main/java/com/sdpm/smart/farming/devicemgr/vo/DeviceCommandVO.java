package com.sdpm.smart.farming.devicemgr.vo;

import com.sdpm.smart.farming.devicemgr.entity.Device;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommand;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommandType;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import org.springframework.beans.BeanUtils;


/**
 * @author shirukai
 */
public class DeviceCommandVO {
    /**
     * 事件ID 唯一主键
     */
    private Integer id;

    /**
     * 设备
     */
    private DeviceVO device;

    /**
     * 指令类型ID
     */
    private DeviceCommandTypeVO type;
    /**
     * 指令内容
     */
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

    public DeviceVO getDevice() {
        return device;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }

    public DeviceCommandTypeVO getType() {
        return type;
    }

    public void setType(DeviceCommandTypeVO type) {
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

    public static DeviceCommandVO toVO(DeviceCommand deviceCommand, DeviceVO deviceVO,DeviceCommandTypeVO typeVO) {
        DeviceCommandVO vo = new DeviceCommandVO();
        BeanUtils.copyProperties(deviceCommand, vo);
        vo.setDevice(deviceVO);
        vo.setType(typeVO);
        return vo;
    }
}
