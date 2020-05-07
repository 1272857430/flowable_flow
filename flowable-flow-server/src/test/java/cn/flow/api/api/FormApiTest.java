package cn.flow.api.api;

import cn.flow.api.enums.EnumNativeActivityType;
import cn.flow.api.request.form.*;
import cn.flow.api.response.form.FormSourceResponse;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.result.Result;
import cn.flow.server.WorkFlowBaseTestCase;
import org.junit.Test;

public class FormApiTest extends WorkFlowBaseTestCase {

    @Test
    public void getActivityFormData(){
//        ActivityFormDataRequest request = new ActivityFormDataRequest("1698713a-903f-11ea-9e8e-d65c5e66a961", EnumNativeActivityType.USER_TASK);
        ActivityFormDataRequest request = new ActivityFormDataRequest("a06ab39e-9042-11ea-9e8e-d65c5e66a961", EnumNativeActivityType.USER_TASK);
        request.setProcessInstanceId("4f88dd3f-900f-11ea-a809-d65c5e66a961");
        request.setProcessDefinitionId("process-test-flow:5:8ebdfa8f-8534-11ea-a57b-d65c5e66a961");
        Result<FormModelResponseBody> result = formApi.getActivityFormData(request);
        assertSuccess(result);
    }


    @Test
    public void getStartFormModel() {
        ProcessStartFormDataRequest requestBody = new ProcessStartFormDataRequest("ba943f36-8565-11ea-aa1e-d65c5e66a961");
        Result<FormModelResponseBody> result = formApi.getStartFormModel(requestBody);
        assertSuccess(result);
    }

    @Test
    public void getRenderedTaskForm() {
        RenderedTaskFormDataRequest requestBody = new RenderedTaskFormDataRequest("1cd0732d-84a3-11ea-a073-d65c5e66a961");
        Result<FormModelResponseBody> result = formApi.getRenderedTaskForm(requestBody);
        assertSuccess(result);
    }

    @Test
    public void getFormModelByKey() {
        FormModelByKeyRequest request = new FormModelByKeyRequest("form-user-hand");
        Result<FormModelResponseBody> result = formApi.getFormModelByKey(request);
        assertSuccess(result);
    }


    @Test
    public void getFormSource() {
        FormSourceRequest requestBody = new FormSourceRequest("form-user-hand");
        Result<FormSourceResponse> result = formApi.getFormSource(requestBody);
        assertSuccess(result);
    }
}