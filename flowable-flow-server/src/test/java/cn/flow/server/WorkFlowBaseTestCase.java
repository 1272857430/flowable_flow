package cn.flow.server;

import cn.flow.api.api.FormApi;
import cn.flow.api.api.ProcessApi;
import cn.flow.api.api.TaskApi;
import cn.flow.api.api.TemplateApi;
import cn.flow.api.request.form.GetFormModelRequestBody;
import cn.flow.api.request.process.StartProcessInstanceRequestBody;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.server.base.BaseTestCase;
import cn.flow.server.base.Mode;
import org.junit.Before;

import java.util.Map;

public abstract class WorkFlowBaseTestCase extends BaseTestCase {

    protected ProcessApi processApi;
    protected TaskApi taskApi;
    protected FormApi formApi;
    protected TemplateApi templateApi;

    @Before
    public void setUp() {
        Mode mode = Mode.LOCAL;
        processApi = todo(ProcessApi.class, mode.getWorkflowUrl());
        taskApi = todo(TaskApi.class, mode.getWorkflowUrl());
        formApi = todo(FormApi.class, mode.getWorkflowUrl());
        templateApi = todo(TemplateApi.class, mode.getWorkflowUrl());
    }

    /**
     * 根据表单key获取表单信息
     */
    public String getFormModelByKey(String formKey){
        GetFormModelRequestBody getFormModelRequestBody = new GetFormModelRequestBody();
        getFormModelRequestBody.setFormkey(formKey);
        FormModelResponseBody formModelByKey = formApi.getFormModelByKey(getFormModelRequestBody);
        assertSuccess(formModelByKey);
        printJsonString(formModelByKey);
        return formModelByKey.getId();
    }

    /**
     * 启动流程，同时提交表单数据
     * @param processBusinessKey  流程BusinessKey
     * @param processInstanceName 流程名称
     * @param processScopeId      数据权限ID
     * @param variables           启动表单数据
     * @return 流程实例数据
     */
    public ProcessInstanceResponseBody startProcessInstanceWithForm(String userId, String processBusinessKey, String processInstanceName, String processScopeId, Map<String, Object> variables) {

        StartProcessInstanceRequestBody startProcessInstanceRequestBody = new StartProcessInstanceRequestBody();
        startProcessInstanceRequestBody.setProcessBusinessKey(processBusinessKey);
        startProcessInstanceRequestBody.setProcessInstanceName(processInstanceName);
        startProcessInstanceRequestBody.setVariables(variables);
        startProcessInstanceRequestBody.setProcessScopeId(processScopeId);
        startProcessInstanceRequestBody.setInitiator(userId);
        return processApi.startProcessInstanceWithForm(startProcessInstanceRequestBody);
    }

}