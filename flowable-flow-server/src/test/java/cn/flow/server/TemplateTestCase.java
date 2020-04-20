package cn.flow.server;

import cn.flow.api.request.template.DeployRequestBody;
import cn.flow.api.response.template.DeployResponseBody;
import cn.flow.api.result.Result;
import cn.flow.server.business_flow.testFlow.TestFlowTemplate;
import org.junit.Test;

public class TemplateTestCase extends WorkFlowBaseTestCase {

    /**
     * 部署流程
     */
    @Test
    public void deployTemplate() {
        Result<DeployResponseBody> deploy = deploy(TestFlowTemplate.class.getName());
        assertSuccess(deploy);
        printJsonString(deploy);
    }

    private Result<DeployResponseBody> deploy(String templateClassName) {
        DeployRequestBody deployRequestBody = new DeployRequestBody();
        deployRequestBody.setTemplateClassName(templateClassName);
        return templateApi.deploy(deployRequestBody);
    }
}
