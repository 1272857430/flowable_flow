package cn.flow.api.request.task;

import lombok.Data;

import java.util.Map;

@Data
public class CompleteTaskRequestBody {

    private String userId;

    private String taskId;

    private String formDefinitionId;

    private String outcome;

    private Map<String, Object> variables;
}
