package cn.flow.api.api;

import cn.flow.api.request.form.*;
import cn.flow.api.response.form.FormSourceResponse;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(value = "/workflow/form", description = "表单管理")
@FeignClient(value = "flowable-flow")
@RequestMapping("/workflow/form")
public interface FormApi {

    @ApiOperation(value = "/getActivityFormData",notes = "获取流程某个节点表单")
    @RequestMapping(value = "/getActivityFormData",method = RequestMethod.POST)
    Result<FormModelResponseBody> getActivityFormData(@RequestBody ActivityFormDataRequest request);

    @ApiOperation(value = "/getStartFormModel",notes = "获取发起表单")
    @RequestMapping(value = "/getStartFormModel",method = RequestMethod.POST)
    Result<FormModelResponseBody> getStartFormModel(@RequestBody ProcessStartFormDataRequest requestBody);

    @ApiOperation(value = "/getRenderedTaskForm",notes = "获取历史任务表单信息")
    @RequestMapping(value = "/getRenderedTaskForm",method = RequestMethod.POST)
    Result<FormModelResponseBody> getRenderedTaskForm(@RequestBody RenderedTaskFormDataRequest request);

    @ApiOperation(value = "/getFormModelByKey", notes = "获取表单定义")
    @RequestMapping(value = "/getFormModelByKey",method = RequestMethod.POST)
    Result<FormModelResponseBody> getFormModelByKey(@RequestBody FormModelByKeyRequest request);

    @RequestMapping(value = "/getFormSource",method = RequestMethod.POST)
    Result<FormSourceResponse> getFormSource(@RequestBody FormSourceRequest request);
}
