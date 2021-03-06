package cn.flow.api.api;

import cn.flow.api.request.task.ClaimTaskRequestBody;
import cn.flow.api.request.task.CompleteTaskRequestBody;
import cn.flow.api.request.task.FindTaskRequestBody;
import cn.flow.api.response.task.EasyTaskInfoResponse;
import cn.flow.api.result.Result;
import cn.flow.server.WorkFlowBaseTestCase;
import org.junit.Test;

import java.util.HashMap;

public class TaskApiTest extends WorkFlowBaseTestCase {

    @Test
    public void claim() {
        ClaimTaskRequestBody requestBody = new ClaimTaskRequestBody();
        requestBody.setTaskId("1cd0732d-84a3-11ea-a073-d65c5e66a961");
        requestBody.setUserId("123456");
        Result result = taskApi.claim(requestBody);
        assertSuccess(result);
    }

    @Test
    public void unClaim() {
        ClaimTaskRequestBody requestBody = new ClaimTaskRequestBody();
        requestBody.setTaskId("1cd0732d-84a3-11ea-a073-d65c5e66a961");
        requestBody.setUserId("123456");
        Result result = taskApi.unClaim(requestBody);
        assertSuccess(result);
    }

    @Test
    public void completeTask(){
        CompleteTaskRequestBody requestBody = new CompleteTaskRequestBody();
        requestBody.setUserId("123456");
        requestBody.setTaskId("0a2cbf7d-8564-11ea-a8b5-d65c5e66a961");
        Result result = taskApi.completeTask(requestBody);
        assertSuccess(result);
    }

    @Test
    public void completeTaskWithForm(){
        CompleteTaskRequestBody requestBody = new CompleteTaskRequestBody();
        requestBody.setUserId("123456");
        requestBody.setTaskId("1cd0732d-84a3-11ea-a073-d65c5e66a961");
        requestBody.setFormDefinitionId("");
        requestBody.setVariables(new HashMap<String, Object>(){{
            put("","");
        }});
        requestBody.setOutcome("");
        Result result = taskApi.completeTaskWithForm(requestBody);
        assertSuccess(result);
    }



    @Test
    public void getTaskByName() {
        FindTaskRequestBody findTaskRequestBody = new FindTaskRequestBody("571f6a7c-8561-11ea-b80d-d65c5e66a961","测试人工节点");
        Result<EasyTaskInfoResponse> result = taskApi.getTaskByName(findTaskRequestBody);
        assertSuccess(result);
    }
}