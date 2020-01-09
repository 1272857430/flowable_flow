package cn.flow.api.response.task;

import lombok.Data;

@Data
public class EasyTaskInfoResponse {

    private String taskId;

    private String taskFormKeyId;

    private String roleKey;

    private String userId;

    public EasyTaskInfoResponse() {
    }

    public EasyTaskInfoResponse(String taskId, String taskFormKeyId, String userId) {
        this.taskId = taskId;
        this.taskFormKeyId = taskFormKeyId;
        this.userId = userId;
    }
}
