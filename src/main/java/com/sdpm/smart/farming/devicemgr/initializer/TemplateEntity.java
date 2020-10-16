package com.sdpm.smart.farming.devicemgr.initializer;

import com.sdpm.smart.farming.devicemgr.entity.DeviceCommandType;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import com.sdpm.smart.farming.devicemgr.entity.DeviceType;

import java.util.List;

/**
 * @author rukey
 */
public class TemplateEntity {
    private List<DeviceType> devices;
    private List<DeviceCommandType> commands;
    private List<DeviceEventType> events;

    public List<DeviceType> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceType> devices) {
        this.devices = devices;
    }

    public List<DeviceCommandType> getCommands() {
        return commands;
    }

    public void setCommands(List<DeviceCommandType> commands) {
        this.commands = commands;
    }

    public List<DeviceEventType> getEvents() {
        return events;
    }

    public void setEvents(List<DeviceEventType> events) {
        this.events = events;
    }
}
