package cn.flow.api.response.form;

import lombok.Data;

@Data
public class FormSourceResponse {

    private String id;

    private String name;

    private byte[] bytes;

    private String deploymentId;
}
