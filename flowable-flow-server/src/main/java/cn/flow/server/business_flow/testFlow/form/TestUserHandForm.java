package cn.flow.server.business_flow.testFlow.form;

import cn.flow.component.form.annotation.Form;
import cn.flow.component.form.annotation.FormField;

@Form(name = "form-user-hand")
public class TestUserHandForm {

    @FormField(name = "备注")
    private String memo;
}
