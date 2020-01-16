package cn.flow.api.api;

import cn.flow.api.request.process.*;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.api.response.process.HistoryActivityInfoResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Api(value = "/workflow/process", description = "流程实例管理")
@FeignClient(value = "flowable-flow")
@RequestMapping("/workflow/process")
public interface ProcessApi {

    @ApiOperation(value = "启动流程")
    @RequestMapping(value = "/startProcessInstanceWithForm", method = RequestMethod.POST)
    ProcessInstanceResponseBody startProcessInstanceWithForm(@RequestBody StartProcessInstanceRequestBody requestBody);

    @ApiOperation(value = "流程展示基本信息")
    @RequestMapping(value = "/getProcessedFormData/{processInstanceId}",method = RequestMethod.GET)
    List<FormModelResponseBody> getProcessedFormData(@PathVariable(value = "processInstanceId") String processInstanceId);

    @ApiOperation(value = "单个流程详细信息")
    @RequestMapping(value = "/getSingleProcessInstanceInfo/{processInstanceId}", method = RequestMethod.GET)
    ProcessInstanceResponseBody getSingleProcessInstanceInfo(@PathVariable("processInstanceId") String processInstanceId);

    @ApiOperation(value = "单个流程参数")
    @RequestMapping(value = "/getProcessVariables/{processInstanceId}", method = RequestMethod.GET)
    Map<String, Object> getProcessVariables(@PathVariable("processInstanceId") String processInstanceId);

    @ApiOperation(value = "我发起的流程")
    @RequestMapping(value = "/getMyStartedProcessInstance",method = RequestMethod.POST)
    List<ProcessInstanceResponseBody> getMyStartedProcessInstance(@RequestBody GetProcessInstanceRequestBody requestBody);

    @ApiOperation(value = "我已处理的流程")
    @RequestMapping(value = "/getMyFinishedProcessInstance/",method = RequestMethod.POST)
    List<ProcessInstanceResponseBody> getMyFinishedProcessInstance(@RequestBody QueryMyFinishInstanceRequestBody requestBody);

    @ApiOperation(value = "历史流程查询")
    @RequestMapping(value = "/getHistoryActivityInfo",method = RequestMethod.POST)
    List<HistoryActivityInfoResponseBody> getHistoryActivityInfo(@RequestBody GetHistoryActivityInfoRequestBody requestBody);

    @RequestMapping(value = "/getActivityFormData",method = RequestMethod.POST)
    FormModelResponseBody getActivityFormData(@RequestBody GetActivityFormDataRequestBody requestBody);

    @ApiOperation(value = "查询流程类型")
    @RequestMapping(value = "/getProcessType",method = RequestMethod.GET)
    List<TypeNode> getProcessType();

    @ApiOperation(value = "根据用户查询流程类型")
    @RequestMapping(value = "/getProcessTypeByUserId/{userId}",method = RequestMethod.GET)
    List<TypeNode> getProcessTypeByUserId(@PathVariable("userId") String userId);

    @ApiOperation(value = "添加流程类型")
    @RequestMapping(value = "/addProcessType",method = RequestMethod.POST)
    TypeNode addProcessType(@RequestBody TypeNode typeNode);

    @ApiOperation(value = "添加流程类型组")
    @RequestMapping(value = "/addProcessTypeGroup",method = RequestMethod.POST)
    TypeNode addProcessTypeGroup(@RequestBody TypeNode typeNode);
}
