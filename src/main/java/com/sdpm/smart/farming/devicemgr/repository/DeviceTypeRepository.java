package com.sdpm.smart.farming.devicemgr.repository;


import com.sdpm.smart.farming.devicemgr.entity.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author rukey
 */
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Integer>, JpaSpecificationExecutor<DeviceType> {
    DeviceType findByType(String type);
}
