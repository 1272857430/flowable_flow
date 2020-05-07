package cn.flow.server.service_api;

import cn.flow.api.enums.EnumNativeActivityType;
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

    Result<FormModelResponseBody> getActivityFormData(ActivityFormDataRequest request) {
        FormModelResponseBody responseBody;
        // 启动表单
        if (EnumNativeActivityType.START_EVENT.equals(request.getActivityType())){
            responseBody = workFlowFormService.getStartFormModel(request.getProcessDefinitionId(), request.getProcessInstanceId());
        // 用户任务表单
        } else if (EnumNativeActivityType.USER_TASK.equals(request.getActivityType())){
            responseBody = workFlowFormService.getTaskFormData(request.getTaskId());
        } else {
            return new Result<FormModelResponseBody>(ResultCode.PARAM_ERROR).setMessage("this activityType not support query taskFormDate");
        }
        return new Result<>(responseBody);
    }

    Result<FormModelResponseBody> getStartFormModel(ProcessStartFormDataRequest requestBody) {
        FormModelResponseBody responseBody = workFlowFormService.getStartFormModel(requestBody.getProcessInstanceId());
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
