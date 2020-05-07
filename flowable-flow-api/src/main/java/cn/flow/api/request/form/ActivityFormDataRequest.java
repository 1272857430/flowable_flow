package cn.flow.api.request.form;

import cn.flow.api.enums.EnumNativeActivityType;
import lombok.Data;

/**
 * 发起表单用processDefinitionId和processInstanceId查询
 * 用户任务表单用taskId查询
 */
@Data
public class ActivityFormDataRequest {

    private String processDefinitionId;

    private String processInstanceId;

    private String taskId;

    private EnumNativeActivityType activityType;

    public ActivityFormDataRequest() {
    }

    public ActivityFormDataRequest(String taskId, EnumNativeActivityType activityType) {
        this.processDefinitionId = processDefinitionId;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.activityType = activityType;
    }

    public ActivityFormDataRequest(String processDefinitionId, String processInstanceId, EnumNativeActivityType activityType) {
        this.processDefinitionId = processDefinitionId;
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.activityType = activityType;
    }
}
