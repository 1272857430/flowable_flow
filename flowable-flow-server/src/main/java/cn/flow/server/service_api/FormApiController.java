package cn.flow.server.service_api;

import cn.flow.api.api.FormApi;
import cn.flow.api.enums.NativeActivityType;
import cn.flow.api.request.form.*;
import cn.flow.api.response.form.FormSourceResponse;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.result.Result;
import cn.flow.api.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/workflow/form")
public class FormApiController implements FormApi {

    @Autowired
    private FormApiService formApiService;

    /**
     * 获取流程某个节点表单
     */
    @Override
    @RequestMapping(value = "/getActivityFormData",method = RequestMethod.POST)
    public Result<FormModelResponseBody> getActivityFormData(@RequestBody ActivityFormDataRequest request) {
        if (Objects.isNull(request.getActivityType())) {
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        return formApiService.getActivityFormData(request);
    }

    /**
     * 获取发起表单
     */
    @Override
    @RequestMapping(value = "/getStartFormModel",method = RequestMethod.POST)
    public Result<FormModelResponseBody> getStartFormModel(@RequestBody ProcessStartFormDataRequest requestBody) {
        if (StringUtils.isEmpty(requestBody.getProcessInstanceId())){
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        return formApiService.getStartFormModel(requestBody);
    }

    /**
     * 获取历史任务表单信息
     */
    @Override
    @RequestMapping(value = "/getRenderedTaskForm",method = RequestMethod.POST)
    public Result<FormModelResponseBody> getRenderedTaskForm(@RequestBody RenderedTaskFormDataRequest request) {
        if (StringUtils.isEmpty(request.getTaskId())){
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        return formApiService.getRenderedTaskForm(request);
    }

    /**
     * 获取表单定义
     */
    @Override
    @RequestMapping(value = "/getFormModelByKey",method = RequestMethod.POST)
    public Result<FormModelResponseBody> getFormModelByKey(@RequestBody FormModelByKeyRequest request) {
        if (StringUtils.isEmpty(request.getFormKey())){
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        return formApiService.getFormModelByKey(request.getFormKey());
    }

    @Override
    @RequestMapping(value = "/getFormSource",method = RequestMethod.POST)
    public Result<FormSourceResponse> getFormSource(@RequestBody FormSourceRequest request) {
        if (StringUtils.isEmpty(request.getFormKey())){
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        return formApiService.getFormSource(request);
    }
}
