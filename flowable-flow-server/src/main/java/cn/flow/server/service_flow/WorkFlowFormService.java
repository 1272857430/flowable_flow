package cn.flow.server.service_flow;

import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.form.vo.OptionFormField;
import cn.flow.component.exception.FlowDeployException;
import cn.flow.component.form.config.CustomFormEngine;
import cn.flow.server.constant.FormKey;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.form.api.FormDefinition;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.engine.impl.persistence.entity.FormResourceEntity;
import org.flowable.form.engine.impl.persistence.entity.FormResourceEntityImpl;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    /**
     * 获取发起表单
     */
    public FormModelResponseBody getStartFormModelByInstanceId(String processInstanceId) {
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
        SimpleFormModel formInfo = (SimpleFormModel) formService.getRenderedTaskForm(taskId, CustomFormEngine.FORM_ENGINE_NAME);
        if (Objects.isNull(formInfo))
            return null;
        FormModelResponseBody responseBody = new FormModelResponseBody();
        BeanUtils.copyProperties(formInfo, responseBody);
        responseBody.setName(taskName);
        return responseBody;
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
    public FormResourceEntity getFormResourceByFormKey(String formKey) {
        FormResourceEntityImpl entity = new FormResourceEntityImpl();
        FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery()
                .formDefinitionKey(formKey).latestVersion().singleResult();
        if (Objects.isNull(formDefinition))
            return null;
        InputStream inputStream =  formRepositoryService.getResourceAsStream(formDefinition.getDeploymentId(), formDefinition.getResourceName());
        if (Objects.isNull(inputStream)) {
            throw new FlowDeployException("Form with formKey '" + formKey + "' does not exist");
        }
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
        entity.setName(formDefinition.getName());
        entity.setDeploymentId(formDefinition.getDeploymentId());

        return entity;
    }
}
