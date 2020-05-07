package cn.flow.api.response.form;

import cn.flow.api.response.form.vo.FormOutcome;
import cn.flow.api.response.form.vo.OptionFormField;
import lombok.Data;

import java.util.List;

@Data
public class FormModelResponseBody {

    private String formDefinitionId;

    private String name;

    private String key;

    private int version;

    private String description;

    private List<OptionFormField> fields;

    private List<FormOutcome> outcomes;

    private String outcomeVariableName;
}
