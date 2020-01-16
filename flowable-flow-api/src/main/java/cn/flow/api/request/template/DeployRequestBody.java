package cn.flow.api.request.template;

import lombok.Data;

@Data
public class DeployRequestBody {

    private String templateClassName;

    private String processKey;

    private String formClassName;
}
