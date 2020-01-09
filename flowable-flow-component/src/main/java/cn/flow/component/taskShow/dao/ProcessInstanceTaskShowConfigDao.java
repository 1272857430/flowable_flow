package cn.flow.component.taskShow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessInstanceTaskShowConfigDao extends JpaRepository<ProcessInstanceTaskShowConfigEntity, String>, JpaSpecificationExecutor<ProcessInstanceTaskShowConfigEntity> {

    ProcessInstanceTaskShowConfigEntity findByInstanceTaskId(String instanceTaskId);
}
