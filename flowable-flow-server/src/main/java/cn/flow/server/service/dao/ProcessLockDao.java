package cn.flow.server.service.dao;

import cn.flow.server.service.entity.ProcessLockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProcessLockDao extends JpaRepository<ProcessLockEntity, String>, JpaSpecificationExecutor<ProcessLockEntity> {

    List<ProcessLockEntity> findByProcessKey(String processKey);
}
