package cn.flow.api.request.form;

import lombok.Data;

public class GetFormModelRequestBody {

    private String formkey;

    public String getFormkey() {
        return formkey;
    }

    public void setFormkey(String formkey) {
        this.formkey = formkey;
    }
}
