package cn.flow.server.service;

import cn.flow.component.form.config.CustomFormEngine;
import cn.flow.component.form.deploy.FormResourceEntity;
import cn.flow.component.form.deploy.FormResourceEntityDao;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.form.api.FormInfo;
import org.flowable.form.api.FormInstance;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.api.FormService;
import org.flowable.form.engine.impl.persistence.entity.FormInstanceEntityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MyFormService {
    private static final Logger logger = LoggerFactory.getLogger(MyFormService.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private org.flowable.engine.FormService formService;

    @Autowired
    private FormService formService2;

    @Autowired
    private FormRepositoryService formRepositoryService;

    @Autowired
    private FormResourceEntityDao formResourceEntityDao;

    public FormInfo getStartFormModel(String processDefinitionId, String processInstanceId){
        FormInfo startFormModel = runtimeService.getStartFormModel(processDefinitionId, processInstanceId);
        return startFormModel;
    }

    public FormInfo getFormModelByKey(String fromKey){
        return formRepositoryService.getFormModelByKey(fromKey);
    }

    public Object getRenderedStartForm(String processDefinitionId){
        return formService.getRenderedStartForm(processDefinitionId, CustomFormEngine.FORM_ENGINE_NAME);
    }

    public Object getRenderedTaskForm(String taskId){
        return formService.getRenderedTaskForm(taskId,CustomFormEngine.FORM_ENGINE_NAME);
    }

    public FormResourceEntity getTaskFormData(String taskId) {
        FormInstance formInstance = formService2.createFormInstanceQuery().taskId(taskId).singleResult();

        if (Objects.isNull(formInstance))
            return null;

        FormInstanceEntityImpl formInstanceEntity = (FormInstanceEntityImpl) formInstance;
        String id  = formInstanceEntity.getResourceRef().getId();
        return formResourceEntityDao.findOne(id);
    }
}