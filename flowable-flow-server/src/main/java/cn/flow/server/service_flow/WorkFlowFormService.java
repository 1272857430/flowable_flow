package cn.flow.server.service_flow;

import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.form.OptionFormField;
import cn.flow.server.constant.FormKey;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.form.api.*;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class WorkFlowFormService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FormRepositoryService formRepositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private FormService formService;

    /**
     * 获取发起表单
     */
    public FormModelResponseBody getStartFormModelByInstanceId(String processInstanceId){
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(historicProcessInstance))
            return null;
        return getStartFormModel(historicProcessInstance.getProcessDefinitionId(), processInstanceId);
    }

    /**
     * 获取发起表单
     */
    public FormModelResponseBody getStartFormModel(String processDefinitionId, String processInstanceId){
        FormInfo formInfo = runtimeService.getStartFormModel(processDefinitionId, processInstanceId);
        if (Objects.isNull(formInfo))
            return null;
        return getFormData(formInfo);
    }

    /**
     * 通过formKey获取form表单
     */
    public FormModelResponseBody getFormModelByKey(String fromKey){
        FormInfo formInfo = formRepositoryService.getFormModelByKey(fromKey);
        if (Objects.isNull(formInfo))
            return null;
        return getFormData(formInfo);
    }

    /**
     * 获取普通任务表单
     */
    public FormModelResponseBody getTaskFormData(String taskId) {
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        return getTaskFormData(taskId, historicTaskInstance.getName());
    }

    /**
     * 获取普通任务表单
     */
    public FormModelResponseBody getTaskFormData(String taskId, String taskName) {
        FormInfo formInfo = taskService.getTaskFormModel(taskId);
        if (Objects.isNull(formInfo))
            return null;
        formInfo.setName(taskName);
        return getFormData(formInfo);
    }

    /**
     * 获取表单数据
     */
    public FormModelResponseBody getFormData(FormInfo formInfo) {
        FormModelResponseBody formModelResponseBody = new FormModelResponseBody();
        List<OptionFormField> formFields = new ArrayList<>();
        SimpleFormModel simpleFormModel = (SimpleFormModel) formInfo.getFormModel();

        BeanUtils.copyProperties(simpleFormModel, formModelResponseBody);
        formModelResponseBody.setId(formInfo.getId());
        for (FormField ele : simpleFormModel.getFields()) {
            OptionFormField field = new OptionFormField();
            BeanUtils.copyProperties(ele, field);
            field.setValue(field.getValue());
            formFields.add(field);
        }
        formModelResponseBody.setFields(formFields);
        // TODO 此处需要改进
        formModelResponseBody.setName(FormKey.FORMS.get(simpleFormModel.getName()));

        return formModelResponseBody;
    }

    /**
     * 获取最新FormResource
     */
    public InputStream getFormResourceByFormKey(String formKey) {
        FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery()
                .formDefinitionKey(formKey).latestVersion().singleResult();
        if (Objects.isNull(formDefinition))
            return null;
        return formRepositoryService.getResourceAsStream(formDefinition.getDeploymentId(), formDefinition.getResourceName());
    }
}
