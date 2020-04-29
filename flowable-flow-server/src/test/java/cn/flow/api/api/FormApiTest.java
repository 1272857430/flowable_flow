package cn.flow.api.api;

import cn.flow.api.request.form.FormModelByKeyRequest;
import cn.flow.api.request.form.FormSourceRequest;
import cn.flow.api.request.form.RenderedTaskFormDataRequest;
import cn.flow.api.request.form.ProcessStartFormDataRequest;
import cn.flow.api.response.form.FormSourceResponse;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.result.Result;
import cn.flow.server.WorkFlowBaseTestCase;
import org.junit.Test;

public class FormApiTest extends WorkFlowBaseTestCase {

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