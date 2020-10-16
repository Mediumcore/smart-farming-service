package com.sdpm.smart.farming.devicemgr.service;

import com.sdpm.smart.farming.devicemgr.entity.DeviceEvent;
import com.sdpm.smart.farming.devicemgr.repository.DeviceEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author shirukai
 */
@Service
public class DeviceEventService {
    @Autowired
    private DeviceEventRepository deviceEventRepository;

    public Page<DeviceEvent> queryDeviceEvent(Integer deviceId, Integer eventTypeId, Long startTime, Long endTime, Pageable pageable) {
        return deviceEventRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (Objects.nonNull(deviceId) && deviceId > 0) {
                list.add(cb.equal(root.get("deviceId"), deviceId));
            }
            if (Objects.nonNull(eventTypeId) && eventTypeId > 0) {
                list.add(cb.equal(root.get("typeId"), eventTypeId));
            }
            if (Objects.nonNull(startTime) && startTime > 0) {
                list.add(cb.greaterThanOrEqualTo(root.get("created"), startTime));
            }
            if (Objects.nonNull(endTime) && endTime > 0) {
                list.add(cb.lessThanOrEqualTo(root.get("created"), endTime));
            }

            return cb.and(list.toArray(new Predicate[0]));
        }, pageable);
    }

    public Long eventCount() {
        return deviceEventRepository.count();
    }

    public DeviceEvent getLastEventByTypeIdAndDeviceId(Integer typeId, Integer deviceId) {
        return deviceEventRepository.findFirstByTypeIdAndDeviceIdOrderByCreatedDesc(typeId, deviceId);
    }


}
