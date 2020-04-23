package cn.flow.server.service_api;

import cn.flow.api.api.FormApi;
import cn.flow.api.request.form.*;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.result.Result;
import cn.flow.api.result.ResultCode;
import cn.flow.server.service_flow.WorkFlowFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.InputStream;
import java.util.Objects;

@Service
public class FormApiService implements FormApi {

    @Autowired
    private WorkFlowFormService workFlowFormService;


    @Override
    public Result<FormModelResponseBody> getStartFormModel(GetStartFormModelRequestBody requestBody) {
        return null;
    }

    @Override
    public Result<FormModelResponseBody> getRenderedStartForm(GetRenderedStartFormRequestBody requestBody) {
        return null;
    }

    @Override
    public Result<FormModelResponseBody> getRenderedTaskForm(GetRenderedTaskFormRequestBody requestBody) {
        return null;
    }

    @Override
    @RequestMapping(value = "/getFormModelByKey/{formKey}",method = RequestMethod.GET)
    public Result<FormModelResponseBody> getFormModelByKey(@PathVariable(value = "formKey") String formKey) {
        FormModelResponseBody formModelResponseBody = workFlowFormService.getFormModelByKey(formKey);
        if (Objects.isNull(formModelResponseBody))
            return new Result<>(ResultCode.NULL_DATA);
        return new Result<>(formModelResponseBody);
    }

    @Override
    @RequestMapping(value = "/getTaskFormData",method = RequestMethod.POST)
    public Result getTaskFormData(@RequestBody GetFormDataRequestBody requestBody) {
        InputStream inputStream = workFlowFormService.getFormResourceByFormKey("form-test-start");
        return null;
    }
}
