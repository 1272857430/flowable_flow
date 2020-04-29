package cn.flow.api.request.form;

import lombok.Data;

@SuppressWarnings("unused")
@Data
public class ProcessStartFormDataRequest {

    private String processInstanceId;

    public ProcessStartFormDataRequest() {
    }

    public ProcessStartFormDataRequest(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
