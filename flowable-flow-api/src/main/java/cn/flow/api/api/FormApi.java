package cn.flow.api.api;

import cn.flow.api.request.form.*;
import cn.flow.api.response.form.FormDataResponseBody;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Api(value = "/workflow/form", description = "表单管理")
@FeignClient(value = "flowable-flow")
@RequestMapping("/workflow/form")
public interface FormApi {

    @RequestMapping(value = "/getStartFormModel",method = RequestMethod.POST)
    Result<FormModelResponseBody> getStartFormModel(@RequestBody GetStartFormModelRequestBody requestBody);

    @RequestMapping(value = "/getRenderedStartForm",method = RequestMethod.POST)
    Result<FormModelResponseBody> getRenderedStartForm(@RequestBody GetRenderedStartFormRequestBody requestBody);

    @RequestMapping(value = "/getRenderedTaskForm",method = RequestMethod.POST)
    Result<FormModelResponseBody> getRenderedTaskForm(@RequestBody GetRenderedTaskFormRequestBody requestBody);

    @ApiOperation(value = "获取表单定义")
    @RequestMapping(value = "/getFormModelByKey/{formKey}",method = RequestMethod.GET)
    Result<FormModelResponseBody> getFormModelByKey(@PathVariable(value = "formKey") String formKey);

    @RequestMapping(value = "/getTaskFormData",method = RequestMethod.POST)
    Result<FormDataResponseBody> getTaskFormData(@RequestBody GetFormDataRequestBody requestBody);
}
