package com.sdpm.smart.farming.devicemgr.repository;

import com.sdpm.smart.farming.devicemgr.entity.DeviceEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author rukey
 */
public interface DeviceEventTypeRepository extends JpaRepository<DeviceEventType, Integer>, JpaSpecificationExecutor<DeviceEventType> {
    DeviceEventType findByType(String type);
}
