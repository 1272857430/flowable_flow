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

    public CompleteTaskRequestBody() {
    }

    public CompleteTaskRequestBody(String userId, String taskId, String formDefinitionId, String outcome, Map<String, Object> variables) {
        this.userId = userId;
        this.taskId = taskId;
        this.formDefinitionId = formDefinitionId;
        this.outcome = outcome;
        this.variables = variables;
    }
}
