package cn.flow.api.response.form;

import lombok.Data;

import java.util.List;

@Data
public class OptionFormField extends FormField{
    protected String optionType;
    protected Boolean hasEmptyValue;
    protected List<Option> options;
    protected String optionsExpression;
}
