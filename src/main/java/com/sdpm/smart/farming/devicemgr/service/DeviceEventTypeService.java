package com.sdpm.smart.farming.devicemgr.service;

import com.sdpm.smart.farming.common.exception.SmartFarmingServiceException;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommand;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommandType;
import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import com.sdpm.smart.farming.devicemgr.repository.DeviceEventRepository;
import com.sdpm.smart.farming.devicemgr.repository.DeviceEventTypeRepository;
import com.sdpm.smart.farming.devicemgr.vo.DeviceEventTypeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author rukey
 */
@Service
public class DeviceEventTypeService {
    @Autowired
    private DeviceEventTypeRepository eventTypeRepository;
    @Autowired
    private DeviceEventRepository eventRepository;

    public Page<DeviceEventType> findDeviceEventTypesWithPage(String typeName, Pageable pageable) {
        return eventTypeRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.hasLength(typeName)) {
                list.add(cb.like(root.get("type"), "%" + typeName + "%"));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, pageable);
    }

    @Cacheable(value = "eventType", key = "#type")
    public DeviceEventType findEventTypeByType(String type) {
        DeviceEventType eventType = eventTypeRepository.findByType(type);
        if (Objects.isNull(eventType)) {
            throw new SmartFarmingServiceException("事件类型不存在。");
        }
        return eventType;
    }

    @Cacheable(value = "eventType", key = "#typeId")
    public DeviceEventType findEventTypeById(Integer typeId) {
        Optional<DeviceEventType> eventTypeOptional = eventTypeRepository.findById(typeId);
        if (!eventTypeOptional.isPresent()) {
            throw new SmartFarmingServiceException("事件类型不存在。");
        }
        return eventTypeOptional.get();
    }

    @CachePut(value = "eventType", key = "#eventType.type")
    public DeviceEventType saveDeviceEventType(DeviceEventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    @CachePut(value = "eventType", key = "#result.id")
    public DeviceEventType addDeviceEventType(DeviceEventTypeVO deviceEventTypeVO) {
        DeviceEventType eventType = eventTypeRepository.findByType(deviceEventTypeVO.getType());
        if (Objects.nonNull(eventType)) {
            throw new SmartFarmingServiceException("设备事件类型已存在。");
        }
        eventType = new DeviceEventType();
        BeanUtils.copyProperties(deviceEventTypeVO, eventType);
        return saveDeviceEventType(eventType);
    }

    @CachePut(value = "eventType", key = "#result.id")
    public DeviceEventType updateDeviceEventType(DeviceEventTypeVO deviceEventTypeVO) {
        DeviceEventType eventType = eventTypeRepository.findByType(deviceEventTypeVO.getType());
        if (Objects.isNull(eventType)) {
            throw new SmartFarmingServiceException("设备事件类型不存在。");
        }
        if (!StringUtils.isEmpty(deviceEventTypeVO.getLabel())) {
            eventType.setLabel(deviceEventTypeVO.getLabel());
        }
        if (!StringUtils.isEmpty(deviceEventTypeVO.getDescription())) {
            eventType.setDescription(deviceEventTypeVO.getDescription());
        }
        return saveDeviceEventType(eventType);
    }

    @CacheEvict(value = "eventType", key = "#eventType.type")
    public void deleteCommandType(DeviceEventType eventType) {
        eventTypeRepository.delete(eventType);
    }

    @Transactional
    @CacheEvict(value = "eventType", key = "#typeId")
    public void removeDeviceEventTypeId(Integer typeId) {
        DeviceEventType eventType = findEventTypeById(typeId);
        if (Objects.isNull(eventRepository.findFirstByTypeId(eventType.getId()))) {
            deleteCommandType(eventType);
        } else {
            throw new SmartFarmingServiceException("实时事件库中存在事件信息，不能删除此事件类型。");
        }
    }
}
