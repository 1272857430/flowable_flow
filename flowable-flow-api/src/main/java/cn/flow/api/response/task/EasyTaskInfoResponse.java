package cn.flow.api.response.task;

import lombok.Data;

@Data
public class EasyTaskInfoResponse {

    private String taskId;

    private String taskFormDefinitionId;

    private String roleKey;

    private String userId;

    public EasyTaskInfoResponse() {
    }

    public EasyTaskInfoResponse(String taskId, String taskFormDefinitionId, String userId) {
        this.taskId = taskId;
        this.taskFormDefinitionId = taskFormDefinitionId;
        this.userId = userId;
    }
}
