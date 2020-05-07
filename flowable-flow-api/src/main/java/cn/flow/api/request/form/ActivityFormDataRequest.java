package cn.flow.api.request.form;

import cn.flow.api.enums.NativeActivityType;
import lombok.Data;

@Data
public class ActivityFormDataRequest {
    private String processDefinitionId;

    private String processInstanceId;

    private String taskId;

    private NativeActivityType activityType;
}
