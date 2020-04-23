package cn.flow.server;

import cn.flow.api.request.template.DeployRequestBody;
import cn.flow.api.response.template.DeployResponseBody;
import cn.flow.api.result.Result;
import cn.flow.server.business_flow.testFlow.TestFlowTemplate;
import org.junit.Test;

public class CreateTestCase extends WorkFlowBaseTestCase {

    /**
     * 自动生成测试用例
     */
    @Test
    public void createTemplateTestCase2(){
        createTestCase(TestFlowTemplate.class.getName(), TestFlowTemplate.PROCESS_KEY);
    }

    private void createTestCase(String templateClassName, String processKey){
        DeployRequestBody deployRequestBody = new DeployRequestBody();
        deployRequestBody.setTemplateClassName(templateClassName);
        deployRequestBody.setProcessKey(processKey);
        Result<DeployResponseBody> result = templateApi.createTestCase(deployRequestBody);
        assertSuccess(result);
    }
}
