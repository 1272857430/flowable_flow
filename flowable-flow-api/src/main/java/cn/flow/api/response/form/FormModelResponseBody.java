package cn.flow.api.response.form;

import lombok.Data;

import java.util.List;

@Data
public class FormModelResponseBody {

    private String id;

    private String name;

    private String key;

    private int version;

    private String description;

    private List<OptionFormField> fields;

    private List<FormOutcome> outcomes;

    private String outcomeVariableName;
}
