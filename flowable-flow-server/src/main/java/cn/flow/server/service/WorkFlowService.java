package cn.flow.server.service;

import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.form.OptionFormField;
import cn.flow.component.form.deploy.FormResourceEntity;
import cn.flow.component.taskShow.dao.ProcessDefinitionTaskShowConfigEntity;
import cn.flow.component.taskShow.service.ProcessDefinitionTaskShowConfigService;
import cn.flow.component.taskShow.service.ProcessInstanceTaskShowConfigService;
import cn.flow.server.constant.FormKey;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.FormInfo;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkFlowService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private MyFormService myFormService;

    @Autowired
    private ProcessDefinitionTaskShowConfigService definitionTaskShowConfigService;

    @Autowired
    private ProcessInstanceTaskShowConfigService instanceTaskShowConfigService;

    /**
     * 获取流程最新版本
     */
    public ProcessDefinition getProcessDefintionByKey(String processDefinitionKey) {
        return repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();
    }

    /**
     * 根据流程ID获取流程实例对象
     */
    public HistoricProcessInstance getProcessInstance(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    /**
     * 查询用户的权限
     */
    public List<String> getProcessScopeIds(String userId) {
        // TODO 需要接入用户权限体系
        return new ArrayList<>();
    }

    /**
     * 获取流程已处理表单数据
     */
    public List<FormModelResponseBody> getProcessedFormData(String processInstanceId) {
        List<FormModelResponseBody>  taskFormDatas = new ArrayList<>();
        // 启动表单信息
        FormModelResponseBody startFormModel = getStartFormModel(processInstanceId);
        taskFormDatas.add(startFormModel);

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .orderByTaskCreateTime().asc()
                .processInstanceId(processInstanceId).list();

        Map<Integer, FormModelResponseBody> forms = new HashMap<>();

        for (HistoricTaskInstance ele : historicTaskInstances) {
            if (Objects.isNull(ele))
                continue;
            ProcessDefinitionTaskShowConfigEntity definitionTaskShowConfigEntity = definitionTaskShowConfigService.searchProcessDefinitionTaskShowConfigEntity(ele.getTaskDefinitionKey());
            // 任务定义展示
            if (!Objects.isNull(definitionTaskShowConfigEntity) && definitionTaskShowConfigEntity.getDisplay()) {
                // 任务实例展示
                if (instanceTaskShowConfigService.judgeTaskIsShow(ele.getId())) {
                    FormModelResponseBody taskFormData = getTaskFormData(ele.getId(), ele.getName(), ele.getFormKey());
                    if (!Objects.isNull(taskFormData)) {
                        forms.put(definitionTaskShowConfigEntity.getDisplayIndex(), taskFormData);
                    }
                }
            }
        }
        forms.forEach((key ,value) -> taskFormDatas.add(value));
        return taskFormDatas;
    }

    /**
     * 获取发起表单
     */
    public FormModelResponseBody getStartFormModel(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = getProcessInstance(processInstanceId);
        FormInfo startFormModel = myFormService.getStartFormModel(historicProcessInstance.getProcessDefinitionId(), processInstanceId);
        if (Objects.isNull(startFormModel))
            return null;
        SimpleFormModel simpleFormModel = (SimpleFormModel) startFormModel.getFormModel();
        return createFormModelResponseBody(simpleFormModel);
    }

    public FormModelResponseBody createFormModelResponseBody(SimpleFormModel simpleFormModel) {
        List<org.flowable.form.model.FormField> fields = simpleFormModel.getFields();
        FormModelResponseBody startFormModelBody = new FormModelResponseBody();
        List<OptionFormField> formFields = new ArrayList<>();

        BeanUtils.copyProperties(simpleFormModel, startFormModelBody);

        for (FormField ele : fields) {
            OptionFormField field = new OptionFormField();
            BeanUtils.copyProperties(ele, field);
            field.setValue(field.getValue());
            formFields.add(field);
        }

        startFormModelBody.setFields(formFields);
        startFormModelBody.setName(FormKey.FORMS.get(simpleFormModel.getName()));
        return startFormModelBody;
    }

    public FormModelResponseBody getTaskFormData(String taskId, String taskName, String formKey) {
        FormResourceEntity formResourceEntity = myFormService.getTaskFormData(taskId);
        FormInfo formInfo = myFormService.getFormModelByKey(formKey);
        if (Objects.isNull(formResourceEntity) || Objects.isNull(formInfo))
            return null;
        return buildFormData(taskName, formInfo, formResourceEntity);
    }

    /**
     * 构建表单数据
     * @param formInfo           表单信息
     * @param formResourceEntity 表单资源实体
     * @return 表单数据
     */
    private FormModelResponseBody buildFormData(String taskName, FormInfo formInfo, FormResourceEntity formResourceEntity) {

        SimpleFormModel formModel = (SimpleFormModel) formInfo.getFormModel();
        FormModelResponseBody responseBody = new FormModelResponseBody();
        List<OptionFormField> formFields = new ArrayList<>();
        Gson gson = new Gson();

        BeanUtils.copyProperties(formModel, responseBody);
        List<FormField> modelFields = formModel.getFields();
        for (FormField ele : modelFields) {
            OptionFormField ff = new OptionFormField();
            BeanUtils.copyProperties(ele, ff);
            Map<String, Object> stringObjectMap = gson.fromJson(JSON.toJSONString(formResourceEntity), Map.class);
            Object o = stringObjectMap.get(ele.getId());
            ff.setValue(o);
            formFields.add(ff);
        }
        responseBody.setName(taskName);
        responseBody.setFields(formFields);
        return responseBody;
    }

}
