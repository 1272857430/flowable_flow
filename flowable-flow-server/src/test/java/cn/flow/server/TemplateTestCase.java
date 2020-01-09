package cn.flow.server;

import cn.flow.api.request.template.DeployRequestBody;
import cn.flow.api.response.template.DeployResponseBody;
import cn.flow.server.business_flow.testFlow.TestFlowTemplate;
import org.junit.Test;

public class TemplateTestCase extends WorkFlowBaseTestCase {

    /**
     * 部署流程
     */
    @Test
    public void deployTemplate() {
        DeployResponseBody deploy = deploy(TestFlowTemplate.PROCESS_KEY);
        assertSuccess(deploy);
        printJsonString(deploy);
    }

    private DeployResponseBody deploy(String templateName) {
        DeployRequestBody deployRequestBody = new DeployRequestBody();
        deployRequestBody.setTemplateName(templateName);
        return templateApi.deploy(deployRequestBody);
    }
}
