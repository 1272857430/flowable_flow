package cn.flow.server.service_api;

import cn.flow.api.request.form.*;
import cn.flow.api.response.form.FormSourceResponse;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.result.Result;
import cn.flow.api.result.ResultCode;
import cn.flow.server.service_flow.WorkFlowFormService;
import org.flowable.form.engine.impl.persistence.entity.FormResourceEntityImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class FormApiService {

    @Autowired
    private WorkFlowFormService workFlowFormService;

    Result<FormModelResponseBody> getStartFormModel(ProcessStartFormDataRequest requestBody) {
        FormModelResponseBody responseBody = workFlowFormService.getStartFormModelByInstanceId(requestBody.getProcessInstanceId());
        return new Result<>(responseBody);
    }

    Result<FormModelResponseBody> getRenderedTaskForm(RenderedTaskFormDataRequest requestBody) {
        FormModelResponseBody responseBody = workFlowFormService.getTaskFormData(requestBody.getTaskId());
        return new Result<>(responseBody);
    }

    Result<FormModelResponseBody> getFormModelByKey(String formKey) {
        FormModelResponseBody formModelResponseBody = workFlowFormService.getFormModelByKey(formKey);
        if (Objects.isNull(formModelResponseBody))
            return new Result<>(ResultCode.NULL_DATA);
        return new Result<>(formModelResponseBody);
    }

    Result<FormSourceResponse> getFormSource(FormSourceRequest request) {
        FormResourceEntityImpl formResourceEntity = (FormResourceEntityImpl) workFlowFormService.getFormResourceByFormKey(request.getFormKey());
        FormSourceResponse formSourceResponse = new FormSourceResponse();
        BeanUtils.copyProperties(formResourceEntity, formSourceResponse);
        return new Result<>(formSourceResponse);
    }
}
