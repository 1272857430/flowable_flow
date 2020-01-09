package cn.flow.api.request.process;

import lombok.Data;

@Data
public class GetActivityFormDataRequestBody {

    private String processDefinitionId;

    private String processInstanceId;

    private String taskId;

    private String activityType;
}
