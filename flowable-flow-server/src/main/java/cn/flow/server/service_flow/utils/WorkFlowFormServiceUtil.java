package cn.flow.server.service_flow.utils;

import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.form.vo.OptionFormField;
import cn.flow.component.exception.FlowDeployException;
import cn.flow.component.utils.IOUtil;
import cn.flow.server.service.vo.FormData;
import com.google.gson.Gson;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.form.api.FormInfo;
import org.flowable.form.engine.impl.persistence.entity.FormResourceEntity;
import org.flowable.form.engine.impl.persistence.entity.FormResourceEntityImpl;
import org.flowable.form.model.FormField;
import org.flowable.form.model.SimpleFormModel;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"WeakerAccess"})
public class WorkFlowFormServiceUtil {

    /**
     * 将 FormInfo 转成 FormModelResponseBody
     */
    public static FormModelResponseBody buildFormData(FormInfo formInfo) {
        SimpleFormModel simpleFormModel = (SimpleFormModel) formInfo.getFormModel();
        return buildFormData(formInfo.getId(), simpleFormModel.getName(), simpleFormModel);
    }

    /**
     * 将 FormInfo 转成 FormModelResponseBody
     * 支持重新定义formName
     */
    public static FormModelResponseBody buildFormData(String formDefinitionId, String formName, SimpleFormModel simpleFormModel) {
        FormModelResponseBody formModelResponseBody = new FormModelResponseBody();
        List<OptionFormField> formFields = new ArrayList<>();

        for (FormField ele : simpleFormModel.getFields()) {
            OptionFormField field = new OptionFormField();
            BeanUtils.copyProperties(ele, field);
            field.setValue(field.getValue());
            formFields.add(field);
        }

        BeanUtils.copyProperties(simpleFormModel, formModelResponseBody);
        formModelResponseBody.setFormDefinitionId(formDefinitionId);
        formModelResponseBody.setFields(formFields);
        if (!StringUtils.isEmpty(formName)){
            formModelResponseBody.setName(formName);
        }
        return formModelResponseBody;
    }

    /**
     * 将 FormInfo 转成 FormModelResponseBody
     */
    public static FormModelResponseBody buildFormData(String taskName, SimpleFormModel formInfo, FormResourceEntity formResourceEntity) {
        FormModelResponseBody responseBody = new FormModelResponseBody();
        BeanUtils.copyProperties(formInfo, responseBody);
        List<OptionFormField> formFields = new ArrayList<>();
        List<org.flowable.form.model.FormField> modelFields = formInfo.getFields();
        Map<String, Object> stringObjectMap = WorkFlowFormServiceUtil.toFormDataMap(formResourceEntity);
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

    /**
     * 将 InputStream 转成 FormResourceEntity
     */
    public static FormResourceEntity getFormResourceEntity(String formKey, InputStream inputStream, String name, String deploymentId) {
        if (Objects.isNull(inputStream)) {
            throw new FlowDeployException(FlowDeployException.FORM_NOT_FOUND_EXCEPTION + formKey);
        }
        FormResourceEntityImpl entity = new FormResourceEntityImpl();
        entity.setBytes(IOUtil.getInputStreamBytes(inputStream));
        entity.setName(name);
        entity.setDeploymentId(deploymentId);
        return entity;
    }

    /**
     * 将 FormResourceEntity TUF-8 转成 map
     */
    public static Map<String, Object> toFormDataMap(FormResourceEntity resourceEntity) {
        byte[] resourceBytes = resourceEntity.getBytes();
        String formTemplateString;
        try {
            formTemplateString = new String(resourceBytes, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new FlowableException("Unsupported encoding of :" + StandardCharsets.UTF_8.name(), e);
        }
        Gson gson = new Gson();
        FormData formData = gson.fromJson(formTemplateString, FormData.class);
        return formData.getValues();
    }
}
