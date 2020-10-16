package com.sdpm.smart.farming.devicemgr.repository;

import com.sdpm.smart.farming.devicemgr.entity.TraceRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author shirukai
 */
public interface TraceRouteRepository extends JpaRepository<TraceRoute, Integer>, JpaSpecificationExecutor<TraceRoute> {
}
