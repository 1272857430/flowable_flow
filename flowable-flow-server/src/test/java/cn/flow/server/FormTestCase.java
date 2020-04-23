package cn.flow.server;

import cn.flow.api.request.template.DeployRequestBody;
import cn.flow.api.result.Result;
import cn.flow.server.business_flow.testFlow.form.TestUserHandForm;
import org.flowable.form.api.FormDeployment;
import org.junit.Test;

public class FormTestCase extends WorkFlowBaseTestCase {

    /**
     * 当某些流程仅仅只需要更新表单时，需要部署单个表单
     */
    @Test
    public void deployTemplate() {
        Result<FormDeployment> result = deployForm(TestUserHandForm.class.getName());
        assertSuccess(result);
    }

    private Result<FormDeployment> deployForm(String formClassName){
        DeployRequestBody deployRequestBody = new DeployRequestBody();
        deployRequestBody.setFormClassName(formClassName);
        return templateApi.deployFormModel(deployRequestBody);
    }
}
