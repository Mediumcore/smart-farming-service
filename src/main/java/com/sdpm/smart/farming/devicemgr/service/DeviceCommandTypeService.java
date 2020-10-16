package com.sdpm.smart.farming.devicemgr.service;

import com.sdpm.smart.farming.common.exception.SmartFarmingServiceException;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommand;
import com.sdpm.smart.farming.devicemgr.entity.DeviceCommandType;
import com.sdpm.smart.farming.devicemgr.repository.DeviceCommandRepository;
import com.sdpm.smart.farming.devicemgr.repository.DeviceCommandTypeRepository;
import com.sdpm.smart.farming.devicemgr.vo.DeviceCommandTypeVO;
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
public class DeviceCommandTypeService {
    @Autowired
    private DeviceCommandTypeRepository commandTypeRepository;
    @Autowired
    private DeviceCommandRepository commandRepository;

    public Page<DeviceCommandType> findDeviceCommandTypesWithPage(String typeName, Pageable pageable) {
        return commandTypeRepository.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.hasLength(typeName)) {
                list.add(cb.like(root.get("type"), "%" + typeName + "%"));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, pageable);
    }

    @Cacheable(value = "commandType", key = "#type")
    public DeviceCommandType findCommandTypeByType(String type) {
        DeviceCommandType commandType = commandTypeRepository.findByType(type);
        if (Objects.isNull(commandType)) {
            throw new SmartFarmingServiceException("指令类型不存在。");
        }
        System.out.println(commandType.getId());
        return commandType;
    }

    @Cacheable(value = "commandType", key = "#typeId")
    public DeviceCommandType findCommandTypeById(Integer typeId) {
        Optional<DeviceCommandType> commandTypeOptional = commandTypeRepository.findById(typeId);
        if (!commandTypeOptional.isPresent()) {
            throw new SmartFarmingServiceException("指令类型不存在。");
        }
        return commandTypeOptional.get();
    }

    @CachePut(value = "commandType", key = "#commandType.type")
    public DeviceCommandType saveDeviceCommandType(DeviceCommandType commandType) {
        return commandTypeRepository.save(commandType);
    }

    @CachePut(value = "commandType", key = "#result.id")
    public DeviceCommandType addDeviceCommandType(DeviceCommandTypeVO deviceCommandTypeVO) {
        DeviceCommandType commandType = commandTypeRepository.findByType(deviceCommandTypeVO.getType());
        if (Objects.nonNull(commandType)) {
            throw new SmartFarmingServiceException("设备指令类型已存在。");
        }
        commandType = new DeviceCommandType();
        BeanUtils.copyProperties(deviceCommandTypeVO, commandType);
        return saveDeviceCommandType(commandType);
    }

    @CachePut(value = "commandType", key = "#result.id")
    public DeviceCommandType updateDeviceCommandType(DeviceCommandTypeVO deviceCommandTypeVO) {
        DeviceCommandType commandType = commandTypeRepository.findByType(deviceCommandTypeVO.getType());
        if (Objects.isNull(commandType)) {
            throw new SmartFarmingServiceException("设备指令类型不存在。");
        }
        if (!StringUtils.isEmpty(deviceCommandTypeVO.getLabel())) {
            commandType.setLabel(deviceCommandTypeVO.getLabel());
        }
        if (!StringUtils.isEmpty(deviceCommandTypeVO.getDescription())) {
            commandType.setDescription(deviceCommandTypeVO.getDescription());
        }
        return saveDeviceCommandType(commandType);
    }

    @CacheEvict(value = "commandType", key = "#commandType.type")
    public void deleteCommandType(DeviceCommandType commandType) {
        commandTypeRepository.delete(commandType);
    }

    @Transactional
    @CacheEvict(value = "commandType", key = "#typeId")
    public void removeDeviceCommandTypeId(Integer typeId) {
        DeviceCommandType deviceCommandType = findCommandTypeById(typeId);
        if (Objects.isNull(commandRepository.findFirstByTypeId(deviceCommandType.getId()))) {
            deleteCommandType(deviceCommandType);
        } else {
            throw new SmartFarmingServiceException("实时指令库中存在指令信息，不能删除此指令类型。");
        }
    }
}
