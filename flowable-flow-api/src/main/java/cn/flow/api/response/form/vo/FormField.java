package cn.flow.api.response.form.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class FormField implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String id;

    protected String name;

    protected String type;

    protected Object value;

    protected boolean required;

    protected boolean readOnly;

    protected boolean overrideId;

    protected String placeholder;

    protected Map<String, Object> params;

    protected LayoutDefinition layout;
}
