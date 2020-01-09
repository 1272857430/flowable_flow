package cn.flow.api.request.form;

import lombok.Data;

@Data
public class GetStartFormModelRequestBody {

    private String processBusinessKey;

    private String processInstanceId;
}
