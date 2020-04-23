package cn.flow.server.service;

import cn.flow.api.request.process.StartProcessInstanceRequestBody;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.server.service_flow.WorkFlowHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.engine.FlowableFormValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service("ProcessHandleService")
public class ProcessService {

    @Autowired
    private WorkFlowHandlerService workFlowHandlerService;

    @Autowired
    private ProcessExtService processExtService;

    @Autowired
    private ProcessStartChanceService processStartChanceService;

    @Autowired
    private ProcessLockService processLockService;

    @Autowired
    private ProcessStartFaildService processStartFaildService;

    /**
     * 发起流程
     */
    @Transactional(rollbackFor = Exception.class)
    public ProcessInstanceResponseBody startProcessInstanceWithForm(StartProcessInstanceRequestBody requestBody) {
        try {
            // TODO 判断流程是否发起
            if (!processStartChanceService.judgeStartProcess(requestBody.getProcessBusinessKey(), requestBody.getProcessScopeId())) {
                log.info("不发起流程");
                return null;
            }
            // TODO 流程加锁，避免业务重复发起，(锁机制：流程key+片区+发起人)
            processLockService.tryLockProcess(requestBody.getProcessBusinessKey(),requestBody.getProcessScopeId(),requestBody.getInitiator());
            // TODO 发起流程
            ProcessInstance processInstance = workFlowHandlerService.startProcessWithForm(requestBody);
            // 保存流程实例在EXT_RU_PROINS
            if(!Objects.isNull(processInstance) && processInstance instanceof ExecutionEntityImpl){
                ProcessInstanceResponseBody processInstanceResponseBody = processExtService.saveProcessInstance(processInstance, requestBody.getProcessScopeId());
                if (!Objects.isNull(processInstanceResponseBody)) {
                    return processInstanceResponseBody;
                } else {
                    throw new Exception("新建流程实例失败");
                }
            } else {
                throw new Exception("新建流程实例失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发起失败，释放锁");
            processLockService.releaseProcessLock(requestBody.getProcessBusinessKey(),requestBody.getProcessScopeId(),requestBody.getInitiator());
            // 保存发起失败的记录
            if (e instanceof FlowableFormValidationException) {
                processStartFaildService.insertData(requestBody, "表单参数有误" + e.getMessage());
                throw new RuntimeException("表单参数有误");
            } else {
                processStartFaildService.insertData(requestBody, e.getMessage() + e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
