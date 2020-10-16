package com.sdpm.smart.farming.devicemgr.repository;

import com.sdpm.smart.farming.devicemgr.entity.DeviceCommandType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author rukey
 */
public interface DeviceCommandTypeRepository extends JpaRepository<DeviceCommandType, Integer>, JpaSpecificationExecutor<DeviceCommandType> {
    DeviceCommandType findByType(String type);
}
