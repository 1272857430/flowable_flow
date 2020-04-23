package cn.flow.server.service_api;

import cn.flow.api.api.FormApi;
import cn.flow.api.request.form.*;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.result.Result;
import cn.flow.api.result.ResultCode;
import cn.flow.server.service_flow.WorkFlowFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Objects;

@Service
public class FormApiService implements FormApi {

    @Autowired
    private WorkFlowFormService workFlowFormService;

    public Result<FormModelResponseBody> getStartFormModel(GetStartFormModelRequestBody requestBody) {
        return null;
    }

    public Result<FormModelResponseBody> getRenderedStartForm(GetRenderedStartFormRequestBody requestBody) {
        return null;
    }

    public Result<FormModelResponseBody> getRenderedTaskForm(GetRenderedTaskFormRequestBody requestBody) {
        return null;
    }

    public Result<FormModelResponseBody> getFormModelByKey(String formKey) {
        FormModelResponseBody formModelResponseBody = workFlowFormService.getFormModelByKey(formKey);
        if (Objects.isNull(formModelResponseBody))
            return new Result<>(ResultCode.NULL_DATA);
        return new Result<>(formModelResponseBody);
    }

    public Result getTaskFormData(GetFormDataRequestBody requestBody) {
        InputStream inputStream = workFlowFormService.getFormResourceByFormKey("form-test-start");
        return null;
    }
}
