package cn.flow.server.service.dao;

import cn.flow.server.service.entity.ProcessExtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProcessExtDao extends JpaRepository<ProcessExtEntity, String>, JpaSpecificationExecutor<ProcessExtEntity> {
}
