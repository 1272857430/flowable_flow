package cn.flow.server.service.dao;

import cn.flow.server.service.entity.ProcessStartChanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProcessStartChanceDao extends JpaRepository<ProcessStartChanceEntity, String>, JpaSpecificationExecutor<ProcessStartChanceEntity> {

    List<ProcessStartChanceEntity> findByProcessKeyAndProcessScopeId(String processKey, String processScopeId);
}
