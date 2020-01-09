package cn.flow.api.request.task;

import lombok.Data;

@Data
public class FindTaskRequestBody {

    private String processInsId;

    private String taskName;

    public FindTaskRequestBody() {
    }

    public FindTaskRequestBody(String processInsId, String taskName) {
        this.processInsId = processInsId;
        this.taskName = taskName;
    }
}
