package cn.flow.api.request.form;

import lombok.Data;

@SuppressWarnings("unused")
@Data
public class RenderedTaskFormDataRequest {

    private String taskId;

    public RenderedTaskFormDataRequest() {
    }

    public RenderedTaskFormDataRequest(String taskId) {
        this.taskId = taskId;
    }
}
