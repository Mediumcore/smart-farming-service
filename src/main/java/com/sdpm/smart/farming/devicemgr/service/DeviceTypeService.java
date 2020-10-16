package com.sdpm.smart.farming.devicemgr.service;

import com.sdpm.smart.farming.common.exception.SmartFarmingServiceException;
import com.sdpm.smart.farming.devicemgr.entity.DeviceType;
import com.sdpm.smart.farming.devicemgr.repository.DeviceTypeRepository;
import com.sdpm.smart.farming.devicemgr.vo.DeviceTypeVO;
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

/**
 * @author rukey
 */
@Service
public class DeviceTypeService {
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    public Page<DeviceType> findDeviceTypesWithPage(String typeName, Pageable pageable) {
        return deviceTypeRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.hasLength(typeName)) {
                list.add(cb.like(root.get("type"), "%" + typeName + "%"));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, pageable);
    }

    @Cacheable(value = "deviceType", key = "#type")
    public DeviceType findDeviceTypeByType(String type) {
        DeviceType deviceType = deviceTypeRepository.findByType(type);
        if (Objects.isNull(deviceType)) {
            throw new SmartFarmingServiceException("设备类型不存在。");
        }
        return deviceType;
    }

    @CachePut(value = "deviceType", key = "#deviceTypeVO.type")
    public DeviceType addDeviceType(DeviceTypeVO deviceTypeVO) {
        DeviceType deviceType = deviceTypeRepository.findByType(deviceTypeVO.getType());
        if (Objects.nonNull(deviceType)) {
            throw new SmartFarmingServiceException("设备类型已存在。");
        }
        deviceType = new DeviceType();
        BeanUtils.copyProperties(deviceTypeVO, deviceType);
        return deviceTypeRepository.save(deviceType);
    }

    @CachePut(value = "deviceType", key = "#deviceTypeVO.type")
    public DeviceType updateDeviceType(DeviceTypeVO deviceTypeVO) {
        DeviceType deviceType = deviceTypeRepository.findByType(deviceTypeVO.getType());
        if (Objects.isNull(deviceType)) {
            throw new SmartFarmingServiceException("设备事件类型不存在。");
        }
        if (!StringUtils.isEmpty(deviceTypeVO.getLabel())) {
            deviceType.setLabel(deviceTypeVO.getLabel());
        }
        if (!StringUtils.isEmpty(deviceTypeVO.getDescription())) {
            deviceType.setDescription(deviceTypeVO.getDescription());
        }
        ;
        return deviceTypeRepository.save(deviceType);
    }

    @Transactional
    @CacheEvict(value = "deviceType", key = "#type")
    public void removeDeviceType(String type) {
        DeviceType deviceType = deviceTypeRepository.findByType(type);
        // 此处性能可能有影响，后期优化。
        if (deviceType.getDevices().isEmpty()) {
            deviceTypeRepository.delete(deviceType);
        } else {
            throw new SmartFarmingServiceException("设备列表中存在此类型设备，不能删除此设备类型。");
        }
    }
}
