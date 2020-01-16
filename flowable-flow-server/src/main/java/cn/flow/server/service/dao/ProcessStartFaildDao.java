package cn.flow.server.service.dao;

import cn.flow.server.service.entity.ProcessStartFaildEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProcessStartFaildDao extends JpaRepository<ProcessStartFaildEntity, String>, JpaSpecificationExecutor<ProcessStartFaildEntity> {
}
