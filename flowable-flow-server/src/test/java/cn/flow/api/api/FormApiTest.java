package cn.flow.api.api;

import cn.flow.api.request.form.GetFormDataRequestBody;
import cn.flow.api.response.form.FormDataResponseBody;
import cn.flow.api.result.Result;
import cn.flow.server.WorkFlowBaseTestCase;
import org.junit.Test;

public class FormApiTest extends WorkFlowBaseTestCase {

    @Test
    public void getTaskFormData() {
        GetFormDataRequestBody requestBody = new GetFormDataRequestBody();
        requestBody.setTaskId("1cd0732d-84a3-11ea-a073-d65c5e66a961");
        Result<FormDataResponseBody> result = formApi.getTaskFormData(requestBody);
        assertSuccess(result);
    }
}