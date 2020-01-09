package cn.flow.component.form.config;

import cn.flow.component.form.deploy.FormResourceEntity;
import cn.flow.component.form.deploy.FormResourceEntityDao;
import com.google.gson.Gson;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.impl.form.FormEngine;
import org.flowable.form.api.FormDefinition;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.api.FormService;
import org.flowable.form.model.SimpleFormModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

public class CustomFormEngine implements FormEngine{
    public static final String FORM_ENGINE_NAME = "cjd-forms-engine";

    @Autowired
    FormResourceEntityDao formResourceEntityDao;

    @Autowired
    FormRepositoryService formRepositoryService;

    @Autowired
    FormService formService;

    @Override
    public String getName() {
        return FORM_ENGINE_NAME;
    }

    @Override
    public Object renderStartForm(StartFormData startForm) {
        if (startForm.getFormKey() == null) {
            return null;
        }
        String formKey = startForm.getFormKey();
        Gson gson = new Gson();
        return gson.fromJson(getFormTemplateString(formKey), SimpleFormModel.class);
    }

    @Override
    public Object renderTaskForm(TaskFormData taskForm) {
        if (taskForm.getFormKey() == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(getFormTemplateString(taskForm.getFormKey()), SimpleFormModel.class);
    }

    private String getFormTemplateString(String formKey) {
        FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery()
                .formDefinitionKey(formKey).latestVersion().singleResult();
        FormResourceEntity resourceStream = formResourceEntityDao.findByDeploymentIdAndName(formDefinition.getDeploymentId(), formDefinition.getResourceName());
        if (resourceStream == null) {
            throw new FlowableObjectNotFoundException("Form with formKey '" + formKey + "' does not exist", String.class);
        }

        byte[] resourceBytes = resourceStream.getBytes();
        String encoding = "UTF-8";
        String formTemplateString = "";
        try {
            formTemplateString = new String(resourceBytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new FlowableException("Unsupported encoding of :" + encoding, e);
        }
        return formTemplateString;
    }

}
