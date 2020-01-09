package cn.flow.component.taskShow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessDefinitionTaskShowConfigDao extends JpaRepository<ProcessDefinitionTaskShowConfigEntity, String>, JpaSpecificationExecutor<ProcessDefinitionTaskShowConfigEntity> {

    List<ProcessDefinitionTaskShowConfigEntity> findByDefinitionTaskId(String definitionTaskId);
}
