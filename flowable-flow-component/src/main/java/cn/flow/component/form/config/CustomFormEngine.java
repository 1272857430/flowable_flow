package cn.flow.component.form.config;

import com.google.gson.Gson;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.impl.form.FormEngine;
import org.flowable.form.api.FormDefinition;
import org.flowable.form.api.FormRepositoryService;
import org.flowable.form.model.SimpleFormModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class CustomFormEngine implements FormEngine{
    public static final String FORM_ENGINE_NAME = "cjd-forms-engine";

    @Autowired
    FormRepositoryService formRepositoryService;

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
        byte[] resourceBytes = getFormResource(formKey);
        String encoding = "UTF-8";
        String formTemplateString = "";
        try {
            formTemplateString = new String(resourceBytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new FlowableException("Unsupported encoding of :" + encoding, e);
        }
        return formTemplateString;
    }

    private byte[] getFormResource(String formKey) {
        FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery()
                .formDefinitionKey(formKey).latestVersion().singleResult();
        InputStream inputStream = formRepositoryService.getResourceAsStream(formDefinition.getDeploymentId(), formDefinition.getResourceName());
        if (Objects.isNull(inputStream)) {
            throw new FlowableObjectNotFoundException("Form with formKey '" + formKey + "' does not exist", String.class);
        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); InputStream is = inputStream) {
            byte[] buffer = new byte[1024];
            int n;
            while (-1 != (n = is.read(buffer))) {
                outputStream.write(buffer, 0, n);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
