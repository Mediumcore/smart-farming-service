package com.sdpm.smart.farming.devicemgr.service;

import com.alibaba.fastjson.JSONObject;
import com.sdpm.smart.farming.devicemgr.entity.Device;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import com.sdpm.smart.farming.devicemgr.vo.DeviceEventTypeVO;
import com.sdpm.smart.farming.devicemgr.vo.DeviceEventVO;
import com.sdpm.smart.farming.devicemgr.vo.DeviceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 实时推送事件到前台
 *
 * @author shirukai
 */
@ServerEndpoint("/api/v1/operating/ws/events/{device_id}")
@Component
public class RealTimeEventsService {
    private static final Logger logger = LoggerFactory.getLogger(RealTimeEventsService.class);
    private static final ConcurrentHashMap<Integer, Session> sessions = new ConcurrentHashMap<>(16);

    @OnOpen
    public void onOpen(Session session, @PathParam("device_id") Integer device_id) {
        sessions.put(device_id, session);
        logger.info("Client is connected,id is {}.", device_id);
    }

    @OnClose
    public void onClose(@PathParam("device_id") Integer device_id) {
        sessions.remove(device_id);
        logger.info("Client is disconnected,id is {}.", device_id);
    }

    @Async
    public void sendEvent(Device device, DeviceEventType eventType, String body,long created) {
        int device_id = device.getId();
        DeviceEventVO eventVO = new DeviceEventVO();
        eventVO.setDevice(DeviceVO.toVO(device));
        eventVO.setType(DeviceEventTypeVO.toVO(eventType));
        eventVO.setBody(body);
        eventVO.setCreated(created);
        Session session = sessions.getOrDefault(device_id, null);
        if (null != session) {
            try {
                session.getBasicRemote().sendText(JSONObject.toJSONString(eventVO));
            } catch (Exception e) {
                logger.error("Send event error", e);
            }

        }
    }

}
