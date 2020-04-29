package cn.flow.api.request.form;

import lombok.Data;

@SuppressWarnings("unused")
@Data
public class FormSourceRequest {

    private String formKey;

    public FormSourceRequest() {
    }

    public FormSourceRequest(String formKey) {
        this.formKey = formKey;
    }
}
