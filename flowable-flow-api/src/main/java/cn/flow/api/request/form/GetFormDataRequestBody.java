package cn.flow.api.request.form;

import lombok.Data;

@Data
public class GetFormDataRequestBody {

    private String taskId;

    private String processInstanceId;
}
