package cn.flow.server.service_flow;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkFlowQueryService {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 获取流程最新版本
     */
    public ProcessDefinition getProcessDefintionByKey(String processDefinitionKey) {
        return repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();
    }
}
