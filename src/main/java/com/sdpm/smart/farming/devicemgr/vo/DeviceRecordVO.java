package com.sdpm.smart.farming.devicemgr.vo;


import com.sdpm.smart.farming.devicemgr.entity.DeviceRecord;
import org.springframework.beans.BeanUtils;

/**
 * @author shirukai
 */
public class DeviceRecordVO {
    /**
     * 设备记录ID
     */
    private Integer id;

    /**
     * 设备IP
     */
    private String ipaddress;

    private DeviceVO device;

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

    public DeviceVO getDevice() {
        return device;
    }

    public void setDevice(DeviceVO device) {
        this.device = device;
    }

    public static DeviceRecordVO toVO(DeviceRecord deviceRecord) {
        DeviceRecordVO recordVO = new DeviceRecordVO();
        BeanUtils.copyProperties(deviceRecord, recordVO);
        recordVO.setDevice(DeviceVO.toVO(deviceRecord.getDevice()));
        return recordVO;
    }
}
