package cn.flow.server.service_flow;

import cn.flow.api.request.process.StartProcessInstanceRequestBody;
import cn.flow.component.form.TranslateFieldAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class WorkFlowHandlerService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private WorkFlowQueryService workFlowQueryService;

    @Autowired
    private IdentityService identityService;

    /**
     * 发起流程
     */
    public ProcessInstance startProcessWithForm(StartProcessInstanceRequestBody requestBody){
        // 获取最新流程版本
        ProcessDefinition processDefinition = workFlowQueryService.getProcessDefintionByKey(requestBody.getProcessBusinessKey());
        identityService.setAuthenticatedUserId(requestBody.getInitiator());
        Map<String, Object> variables = requestBody.getVariables();
        variables.put(TranslateFieldAnnotation.PROCESS_SCOPE_ID, requestBody.getProcessScopeId());
        variables.put(TranslateFieldAnnotation.OUT_COME, requestBody.getOutcome());
        log.info("流程参数: {}", requestBody.getVariables());
        return runtimeService.startProcessInstanceWithForm(processDefinition.getId(), "outcome", variables, requestBody.getProcessInstanceName());
    }
}
