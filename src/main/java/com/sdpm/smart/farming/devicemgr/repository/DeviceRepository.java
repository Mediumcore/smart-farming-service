package com.sdpm.smart.farming.devicemgr.repository;

import com.sdpm.smart.farming.devicemgr.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author rukey
 */
public interface DeviceRepository extends JpaRepository<Device,Integer>, JpaSpecificationExecutor<Device> {

}
