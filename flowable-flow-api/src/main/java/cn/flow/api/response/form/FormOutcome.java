package cn.flow.api.response.form;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FormOutcome implements Serializable {
    private static final long serialVersionUID = 1L;
    protected Group group;
    protected List<String> hidden;

    public FormOutcome() {
    }
}
