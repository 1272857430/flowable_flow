package cn.flow.server.service_flow;

import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.form.vo.OptionFormField;
import cn.flow.component.exception.FlowDeployException;
import cn.flow.component.form.config.CustomFormEngine;
import cn.flow.server.constant.FormKey;
import cn.flow.server.service.vo.FormData;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.form.api.FormDefinition;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormInstance;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.engine.impl.persistence.entity.FormInstanceEntityImpl;
import org.flowable.form.engine.impl.persistence.entity.FormResourceEntity;
import org.flowable.form.engine.impl.persistence.entity.FormResourceEntityImpl;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
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
        return getFormData(formInfo);
    }

    private FormModelResponseBody getFormData(FormInfo formInfo) {
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
     * 通过formKey获取form表单
     */
    public FormModelResponseBody getFormModelByKey(String fromKey) {
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
//        SimpleFormModel formInfo = (SimpleFormModel) formService.getRenderedTaskForm(taskId, CustomFormEngine.FORM_ENGINE_NAME);
//        if (Objects.isNull(formInfo))
//            return null;
//        FormModelResponseBody responseBody = new FormModelResponseBody();
//        BeanUtils.copyProperties(formInfo, responseBody);
//        responseBody.setName(taskName);
//        return responseBody;
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (!Objects.isNull(task)) {
            if (StringUtils.isBlank(task.getFormKey())) {
                return null;
            }
            return getTaskFormData(taskId, task.getName(), task.getFormKey());
        } else {
            HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
            if (StringUtils.isBlank(historicTaskInstance.getFormKey())) {
                return null;
            }
            if (!Objects.isNull(historicTaskInstance)) {
                return getTaskFormData(taskId, historicTaskInstance.getName(), historicTaskInstance.getFormKey());
            } else {
                return null;
            }
        }
    }

    public FormModelResponseBody getTaskFormData(String taskId, String taskName, String formKey) {
        FormResourceEntity formResourceEntity = getFormResourceByTaskId(taskId);
        FormInfo formInfo = formRepositoryService.getFormModelByKey(formKey);
        if (!Objects.isNull(formResourceEntity) && !Objects.isNull(formInfo)) {
            return buildFormData(taskName, formInfo, formResourceEntity);
        }
        return null;
    }

    private FormModelResponseBody buildFormData(String taskName, FormInfo formInfo, FormResourceEntity formResourceEntity) {
        SimpleFormModel formModel = (SimpleFormModel) formInfo.getFormModel();
        FormModelResponseBody responseBody = new FormModelResponseBody();
        BeanUtils.copyProperties(formModel, responseBody);
        List<OptionFormField> formFields = new ArrayList<>();
        List<org.flowable.form.model.FormField> modelFields = formModel.getFields();
        Map<String, Object> stringObjectMap = toFormDataMap(formResourceEntity);
        for (FormField modelField : modelFields) {
            OptionFormField ff = new OptionFormField();
            BeanUtils.copyProperties(modelField, ff);
            ff.setValue(stringObjectMap.get(modelField.getId()));
            formFields.add(ff);
        }
        responseBody.setName(taskName);
        responseBody.setFields(formFields);
        return responseBody;
    }

    private Map<String, Object> toFormDataMap(FormResourceEntity resourceEntity) {
        byte[] resourceBytes = resourceEntity.getBytes();
        String encoding = "UTF-8";
        String formTemplateString;
        try {
            formTemplateString = new String(resourceBytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new FlowableException("Unsupported encoding of :" + encoding, e);
        }
        Gson gson = new Gson();
        FormData formData = gson.fromJson(formTemplateString, FormData.class);
        return formData.getValues();
    }

    /**
     * 获取任务ID对应的FormResource
     */
    public FormResourceEntity getFormResourceByTaskId(String taskId) {
        HistoricTaskInstanceEntityImpl taskInstanceEntity = (HistoricTaskInstanceEntityImpl) historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        FormInstanceEntityImpl formInstance = (FormInstanceEntityImpl) formServiceApi.createFormInstanceQuery().taskId(taskId).singleResult();
        InputStream inputStream = formRepositoryService.getFormDefinitionResource(formInstance.getFormDefinitionId());
        return getFormResourceEntity(inputStream, taskInstanceEntity.getName(), "");
    }

    /**
     * 获取最新FormResource
     */
    public FormResourceEntity getFormResourceByFormKey(String formKey) {
        FormResourceEntityImpl entity = new FormResourceEntityImpl();
        FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery()
                .formDefinitionKey(formKey).latestVersion().singleResult();
        if (Objects.isNull(formDefinition))
            return null;
        InputStream inputStream = formRepositoryService.getResourceAsStream(formDefinition.getDeploymentId(), formDefinition.getResourceName());
        if (Objects.isNull(inputStream)) {
            throw new FlowDeployException("Form with formKey '" + formKey + "' does not exist");
        }
        return entity;
    }

    private FormResourceEntity getFormResourceEntity(InputStream inputStream, String name, String deploymentId) {
        FormResourceEntityImpl entity = new FormResourceEntityImpl();
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); InputStream is = inputStream) {
            byte[] buffer = new byte[1024];
            int n;
            while (-1 != (n = is.read(buffer))) {
                outputStream.write(buffer, 0, n);
            }
            entity.setBytes(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        entity.setName(name);
        entity.setDeploymentId(deploymentId);

        return entity;
    }
}
