package cn.flow.server.business_flow.testFlow.form;


import cn.flow.component.form.annotation.Form;
import cn.flow.component.form.annotation.FormField;

@Form(name = "form-test-start")
public class TestStartForm {

    @FormField(name = "备注")
    private String memo;
}
