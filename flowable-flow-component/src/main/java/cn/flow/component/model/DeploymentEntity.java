package cn.flow.component.model;

import lombok.Data;
import org.flowable.common.engine.api.repository.EngineResource;

import java.util.Date;
import java.util.Map;

@Data
public class DeploymentEntity {

    private String id;

    private String name;

    private String category;

    private String key;

    private Map<String, EngineResource> resources;

    private Date deploymentTime;

    private boolean isNew;

    private String derivedFrom;

    private String derivedFromRoot;

    private String parentDeploymentId;

    private boolean hasError;

    private String errorMessage;

    private boolean isOk;

    private String xml;

    public DeploymentEntity() {
        this.hasError = false;
        this.isOk = true;
    }
}
