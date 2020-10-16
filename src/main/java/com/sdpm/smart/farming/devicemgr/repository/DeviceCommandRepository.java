package com.sdpm.smart.farming.devicemgr.repository;

import com.sdpm.smart.farming.devicemgr.entity.DeviceCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rukey
 */
public interface DeviceCommandRepository extends JpaRepository<DeviceCommand, Integer>, JpaSpecificationExecutor<DeviceCommand> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM device_command WHERE device_id=?",nativeQuery = true)
    void deleteAllByDeviceId(Integer deviceId);

    DeviceCommand findFirstByTypeId(Integer typeId);
}
