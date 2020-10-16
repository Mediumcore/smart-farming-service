package com.sdpm.smart.farming.devicemgr.repository;

import com.sdpm.smart.farming.devicemgr.entity.DeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author shirukai
 */
public interface DeviceRecordRepository extends JpaRepository<DeviceRecord, Integer>, JpaSpecificationExecutor<DeviceRecord> {
}
