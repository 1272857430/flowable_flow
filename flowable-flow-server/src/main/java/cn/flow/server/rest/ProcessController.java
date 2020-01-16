package cn.flow.server.rest;

import cn.flow.api.api.ProcessApi;
import cn.flow.api.request.process.*;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.process.HistoryActivityInfoResponseBody;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.server.service.*;
import cn.flow.server.service.dao.ProcessExtDao;
import cn.flow.server.utils.IdAssertUtil;
import io.swagger.annotations.ApiOperation;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.form.engine.FlowableFormValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/workflow/process")
public class ProcessController implements ProcessApi {

    private static final Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
   private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private WorkFlowService workFlowTaskService;

    @Autowired
    private ProcessExtService processExtService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ProcessExtDao processExtDao;

    @Autowired
    private ProcessLockService processLockService;

    @Autowired
    private ProcessStartChanceService processStartChanceService;

    @Autowired
    private ProcessStartFaildService processStartFaildService;

    @Override
    @ApiOperation(value = "启动流程")
    @RequestMapping(value = "/startProcessInstanceWithForm", method = RequestMethod.POST)
    public ProcessInstanceResponseBody startProcessInstanceWithForm(@RequestBody StartProcessInstanceRequestBody requestBody) {
        if (StringUtils.isEmpty(requestBody.getProcessScopeId())) {
            processStartFaildService.insertData(requestBody, "片区ID不能为空");
            throw new RuntimeException("片区ID不能为空");
        }

        if (!IdAssertUtil.assertIdWidthIs32(requestBody.getInitiator())) {
            processStartFaildService.insertData(requestBody, "发起人ID不合法");
            throw new RuntimeException("发起人ID不合法");
        }
        // TODO 判断流程是否发起
        if (!processStartChanceService.judgeStartProcess(requestBody.getProcessBusinessKey(), requestBody.getProcessScopeId())) {
            logger.info("不发起流程");
            return null;
        }

        // TODO 流程加锁，避免业务重复发起，(锁机制：流程key+片区+发起人)
        try {
            processLockService.tryLockProcess(requestBody.getProcessBusinessKey(),requestBody.getProcessScopeId(),requestBody.getInitiator());
        }catch (Exception e) {
            processStartFaildService.insertData(requestBody, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        try {
            Map<String, Object> variables = requestBody.getVariables();
            variables.put("processScopeId", requestBody.getProcessScopeId());
            variables.put("startUserId", requestBody.getInitiator());
            String processInstanceName = requestBody.getProcessInstanceName();
            return processExtService.startProcessInstanceWithForm(requestBody.getProcessBusinessKey(), requestBody.getInitiator(), processInstanceName, requestBody.getProcessScopeId(), variables);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("发起失败，释放锁");
            processLockService.releaseProcessLock(requestBody.getProcessBusinessKey(),requestBody.getProcessScopeId(),requestBody.getInitiator());
            if (e instanceof FlowableFormValidationException) {
                processStartFaildService.insertData(requestBody, "表单参数有误" + e.getMessage());
                throw new RuntimeException("表单参数有误");
            } else {
                processStartFaildService.insertData(requestBody, e.getMessage());
                return null;
            }
        }
    }

    @Override
    @ApiOperation(value = "流程展示基本信息")
    @RequestMapping(value = "/getProcessedFormData/{processInstanceId}", method = RequestMethod.POST)
    public List<FormModelResponseBody> getProcessedFormData(@PathVariable(value = "processInstanceId") String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)){
            return null;
        }
        List<FormModelResponseBody> processedFormData = workFlowTaskService.getProcessedFormData(processInstanceId);
        if (CollectionUtils.isEmpty(processedFormData)) {
            return null;
        }
        return processedFormData;
    }

    @Override
    @ApiOperation(value = "单个流程详细信息")
    @RequestMapping(value = "/getSingleProcessInstanceInfo/{processInstanceId}", method = RequestMethod.GET)
    public ProcessInstanceResponseBody getSingleProcessInstanceInfo(@PathVariable("processInstanceId") String processInstanceId) {
        return null;
    }

    @Override
    @ApiOperation(value = "单个流程参数")
    @RequestMapping(value = "/getProcessVariables/{processInstanceId}", method = RequestMethod.GET)
    public Map<String, Object> getProcessVariables(@PathVariable("processInstanceId") String processInstanceId) {
        return null;
    }

    @Override
    @ApiOperation(value = "我发起的流程")
    @RequestMapping(value = "/getMyFinishedProcessInstance/",method = RequestMethod.POST)
    public List<ProcessInstanceResponseBody> getMyStartedProcessInstance(@RequestBody GetProcessInstanceRequestBody requestBody) {
        return null;
    }

    @Override
    @ApiOperation(value = "我已处理的流程")
    @RequestMapping(value = "/getMyFinishedProcessInstance/",method = RequestMethod.POST)
    public List<ProcessInstanceResponseBody> getMyFinishedProcessInstance(@RequestBody QueryMyFinishInstanceRequestBody requestBody) {
        return null;
    }

    @Override
    @ApiOperation(value = "历史流程查询")
    @RequestMapping(value = "/getHistoryActivityInfo",method = RequestMethod.POST)
    public List<HistoryActivityInfoResponseBody> getHistoryActivityInfo(@RequestBody GetHistoryActivityInfoRequestBody requestBody) {
        return null;
    }

    @Override
    @RequestMapping(value = "/getActivityFormData",method = RequestMethod.POST)
    public FormModelResponseBody getActivityFormData(@RequestBody GetActivityFormDataRequestBody requestBody) {
        return null;
    }

    @Override
    public List<TypeNode> getProcessType() {
        return null;
    }

    @Override
    @RequestMapping(value = "/getProcessTypeByUserId/{userId}",method = RequestMethod.GET)
    public List<TypeNode> getProcessTypeByUserId(@PathVariable("userId") String userId) {
        return null;
    }

    @Override
    @RequestMapping(value = "/addProcessType",method = RequestMethod.POST)
    public TypeNode addProcessType(@RequestBody TypeNode typeNode) {
        return null;
    }

    @Override
    @RequestMapping(value = "/addProcessTypeGroup",method = RequestMethod.POST)
    public TypeNode addProcessTypeGroup(@RequestBody TypeNode typeNode) {
        return null;
    }
}
