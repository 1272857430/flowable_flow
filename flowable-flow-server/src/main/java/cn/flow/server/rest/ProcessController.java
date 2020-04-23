package cn.flow.server.rest;

import cn.flow.api.api.ProcessApi;
import cn.flow.api.request.process.*;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.process.HistoryActivityInfoResponseBody;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.api.result.Result;
import cn.flow.api.result.ResultCode;
import cn.flow.server.service.*;
import cn.flow.server.service_flow.WorkFlowService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/workflow/process")
public class ProcessController implements ProcessApi {

    @Autowired
    @Qualifier("ProcessHandleService")
    private ProcessService processService;

    @Autowired
    private WorkFlowService workFlowTaskService;

    @Autowired
    private ProcessExtService processExtService;

    @Override
    @ApiOperation(value = "启动流程")
    @RequestMapping(value = "/startProcessInstanceWithForm", method = RequestMethod.POST)
    public Result<ProcessInstanceResponseBody> startProcessInstanceWithForm(@RequestBody StartProcessInstanceRequestBody requestBody) {
        try {
            ProcessInstanceResponseBody processInstanceResponseBody = processService.startProcessInstanceWithForm(requestBody);
            return new Result<>(processInstanceResponseBody);
        } catch (Exception e) {
            return new Result<>(ResultCode.SYS_ERROR);
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
        if (StringUtils.isEmpty(processInstanceId)) {
            return null;
        }
        ProcessInstanceResponseBody singleProcessInstanceInfo = processExtService.getSingleProcessInstanceInfo(processInstanceId);
        return singleProcessInstanceInfo;
    }

    @Override
    @ApiOperation(value = "单个流程参数")
    @RequestMapping(value = "/getProcessVariables/{processInstanceId}", method = RequestMethod.GET)
    public Map<String, Object> getProcessVariables(@PathVariable("processInstanceId") String processInstanceId) {
        if (StringUtils.isEmpty(processInstanceId)) {
            return null;
        }
        return workFlowTaskService.getProcessVariables(processInstanceId);
    }

    @Override
    @ApiOperation(value = "我发起的流程")
    @RequestMapping(value = "/getMyStartedProcessInstance/",method = RequestMethod.POST)
    public List<ProcessInstanceResponseBody> getMyStartedProcessInstance(@RequestBody GetProcessInstanceRequestBody requestBody) {
        if (StringUtils.isEmpty(requestBody.getUserId())) {
            throw new RuntimeException("start userId is invalid");
        }
        try {
            // TODO 需要接入用户权限体系 查询用户片区权限
            List<String> processScopeIds = new ArrayList<>();
            // 动态条件查询
            List<ProcessInstanceResponseBody> myStartedProcessInstance = processExtService.getMyStartedProcessInstance(requestBody);
            if (!Objects.isNull(myStartedProcessInstance)) {
                return myStartedProcessInstance;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("start userId is invalid");
        }
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
    @ApiOperation(value = "查询流程类型")
    @RequestMapping(value = "/getProcessType",method = RequestMethod.GET)
    public List<TypeNode> getProcessType() {
        return null;
    }

    @Override
    @ApiOperation(value = "根据用户查询流程类型")
    @RequestMapping(value = "/getProcessTypeByUserId/{userId}",method = RequestMethod.GET)
    public List<TypeNode> getProcessTypeByUserId(@PathVariable("userId") String userId) {
        return null;
    }

    @Override
    @ApiOperation(value = "添加流程类型")
    @RequestMapping(value = "/addProcessType",method = RequestMethod.POST)
    public TypeNode addProcessType(@RequestBody TypeNode typeNode) {
        return null;
    }

    @Override
    @ApiOperation(value = "添加流程类型组")
    @RequestMapping(value = "/addProcessTypeGroup",method = RequestMethod.POST)
    public TypeNode addProcessTypeGroup(@RequestBody TypeNode typeNode) {
        return null;
    }
}
