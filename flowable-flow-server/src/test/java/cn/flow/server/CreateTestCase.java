package cn.flow.server;

import cn.flow.api.response.template.DeployResponseBody;
import cn.flow.server.business_flow.testFlow.TestFlowTemplate;
import org.junit.Test;

public class CreateTestCase extends WorkFlowBaseTestCase {

    /**
     * 自动生成测试用例
     */
    @Test
    public void createTemplateTestCase2(){
       DeployResponseBody createTestCase = templateApi.createTestCase(TestFlowTemplate.PROCESS_KEY);
        printJsonString(createTestCase);
    }
}
