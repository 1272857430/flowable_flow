package cn.flow.server.service_flow;

import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.component.exception.FlowDeployException;
import cn.flow.component.exception.FlowTaskException;
import cn.flow.component.form.config.CustomFormEngine;
import cn.flow.server.constant.FormKey;
import cn.flow.server.service_flow.utils.WorkFlowFormServiceUtil;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.form.api.FormDefinition;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.engine.impl.persistence.entity.FormInstanceEntityImpl;
import org.flowable.form.engine.impl.persistence.entity.FormResourceEntity;
import org.flowable.form.engine.impl.persistence.entity.FormResourceEntityImpl;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@SuppressWarnings({"WeakerAccess", "SpringJavaAutowiredFieldsWarningInspection"})
@Service
public class WorkFlowFormService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private FormRepositoryService formRepositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private FormService formService;

    @Autowired
    private org.flowable.form.api.FormService formServiceApi;

    @Autowired
    private TaskService taskService;

    /**
     * 获取发起表单
     */
    public FormModelResponseBody getStartFormModel(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(historicProcessInstance))
            return null;
        return getStartFormModel(historicProcessInstance.getProcessDefinitionId(), processInstanceId);
    }

    /**
     * 获取发起表单
     */
    public FormModelResponseBody getStartFormModel(String processDefinitionId, String processInstanceId) {
        FormInfo formInfo = runtimeService.getStartFormModel(processDefinitionId, processInstanceId);
        if (Objects.isNull(formInfo))
            return null;
        SimpleFormModel simpleFormModel = (SimpleFormModel) formInfo.getFormModel();
        // TODO 此处需要改进, formName 可以定义成中文或则其他处理
        String formName = FormKey.FORMS.get(simpleFormModel.getName());
        return WorkFlowFormServiceUtil.buildFormData(formInfo.getId(), formName, simpleFormModel);
    }

    /**
     * 获取普通任务表单
     */
    public FormModelResponseBody getTaskFormData(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (!Objects.isNull(task)) {
            // 进行中的任务
            return getTaskFormData(task);
        } else {
            // 已经完成的任务
            return getFinisHTaskFormData(taskId);
        }
    }

    /**
     * 获取普通任务表单(进行中的任务)
     */
    public FormModelResponseBody getTaskFormData(Task task) {
        SimpleFormModel simpleFormModel = (SimpleFormModel) formService.getRenderedTaskForm(task.getId(), CustomFormEngine.FORM_ENGINE_NAME);
        if (Objects.isNull(simpleFormModel))
            return null;
        // TODO 看此处能否添加 formDefinitionId
        return WorkFlowFormServiceUtil.buildFormData(null, task.getName(), simpleFormModel);
    }

    /**
     * 获取普通任务表单(历史任务)
     */
    public FormModelResponseBody getFinisHTaskFormData(String taskId) {
        FormInfo formInfo = taskService.getTaskFormModel(taskId);
        if (Objects.isNull(formInfo))
            return null;
        SimpleFormModel simpleFormModel = (SimpleFormModel) formInfo.getFormModel();
        HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        FormResourceEntity formResourceEntity = getFormResourceByTask(taskId);
        return WorkFlowFormServiceUtil.buildFormData(taskInstance.getName(), simpleFormModel, formResourceEntity);
    }

    /**
     * 获取task对应的FormResource
     */
    public FormResourceEntity getFormResourceByTask(String taskId) {
        HistoricTaskInstanceEntityImpl taskInstanceEntity = (HistoricTaskInstanceEntityImpl) historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (Objects.isNull(taskInstanceEntity)) {
            throw new FlowTaskException(FlowTaskException.TASK_NOT_FOUND_MESSAGE + taskId);
        }
        // TODO 任务未完成返回 目前返回null， 此处需要扩展
        if (Objects.isNull(taskInstanceEntity.getEndTime())) {
            return null;
        }
        return getFormResourceByTask(taskInstanceEntity);
    }

    /**
     * 已经完成的任务 获取task对应的FormResource
     */
    public FormResourceEntity getFormResourceByTask(HistoricTaskInstance task) {
        FormInstanceEntityImpl formInstance = (FormInstanceEntityImpl) formServiceApi.createFormInstanceQuery().taskId(task.getId()).singleResult();
        InputStream inputStream = formRepositoryService.getFormDefinitionResource(formInstance.getFormDefinitionId());
        // TODO 此处需要补充 deployment信息
        return WorkFlowFormServiceUtil.getFormResourceEntity(task.getFormKey(), inputStream, task.getName(), null);
    }

    /**
     * 获取formKey最新FormResource
     */
    public FormResourceEntity getFormResourceByFormKey(String formKey) {
        FormResourceEntityImpl entity = new FormResourceEntityImpl();
        FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery()
                .formDefinitionKey(formKey).latestVersion().singleResult();
        if (Objects.isNull(formDefinition))
            return null;
        InputStream inputStream = formRepositoryService.getResourceAsStream(formDefinition.getDeploymentId(), formDefinition.getResourceName());
        if (Objects.isNull(inputStream)) {
            throw new FlowDeployException(FlowDeployException.FORM_NOT_FOUND_EXCEPTION + formKey);
        }
        return entity;
    }

    /**
     * 通过formKey获取form表单
     */
    public FormModelResponseBody getFormModelByKey(String fromKey) {
        FormInfo formInfo = formRepositoryService.getFormModelByKey(fromKey);
        if (Objects.isNull(formInfo))
            return null;
        return WorkFlowFormServiceUtil.buildFormData(formInfo);
    }

}
