package cn.flow.api.request.form;

import lombok.Data;

@Data
public class FormModelByKeyRequest {

    private String formKey;

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public FormModelByKeyRequest() {
    }

    public FormModelByKeyRequest(String formKey) {
        this.formKey = formKey;
    }
}
