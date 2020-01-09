package cn.flow.api.request.task;

import lombok.Data;

@Data
public class ClaimTaskRequestBody {

    private String userId;

    private String taskId;
}
