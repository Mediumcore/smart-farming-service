package com.sdpm.smart.farming.devicemgr.repository;

import com.sdpm.smart.farming.devicemgr.entity.DeviceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rukey
 */
public interface DeviceEventRepository extends JpaRepository<DeviceEvent, Integer>, JpaSpecificationExecutor<DeviceEvent> {

    DeviceEvent findFirstByTypeId(Integer typeId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM device_event WHERE device_id=?", nativeQuery = true)
    void deleteAllByDeviceId(Integer deviceId);

    DeviceEvent findFirstByTypeIdAndDeviceIdOrderByCreatedDesc(Integer typeId, Integer deviceId);
}
