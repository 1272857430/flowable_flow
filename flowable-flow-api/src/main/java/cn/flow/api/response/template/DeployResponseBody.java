package cn.flow.api.response.template;

import lombok.Data;

import java.util.Date;

@Data
public class DeployResponseBody {

    private String id;

    private String name;

    private String category;

    private String key;

    private Date deploymentTime;

    private boolean isNew;

    private String derivedFrom;

    private String derivedFromRoot;

    private String parentDeploymentId;

    private String errorMessage;

    private boolean isOk;

    private String xml;
}
