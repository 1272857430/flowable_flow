package cn.flow.server.service.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "EXT_RU_PROINS")
public class ProcessExtEntity extends BaseEntityBean{

    @Column(name = "PROCESS_INSTANCE_ID")
    private String processInstanceId;

    @Column(name = "PROCESS_SCOPE_ID")
    private String processScopeId;

    @Column(name = "PROCESS_NUMBER")
    private String processNumber;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "START_USER_ID")
    private String startUserId;

    @Column(name = "START_USER_NAME")
    private String startUserName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PROCESS_NAME")
    private String processInstanceName;

    @Column(name = "PROCESS_DEFINITION_ID")
    private String processDefinitionId;

    @Column(name = "PROCESS_DEFINITION_NAME")
    private String processDefinitionName;

    @Column(name = "PROCESS_DEFINITION_KEY")
    private String processDefinitionKey;

    @Column(name = "PROCESS_DEFINITION_VERSION")
    private Integer processDefinitionVersion;

    @Column(name = "DEPLOYMENT_ID")
    private String deploymentId;

    @Column(name = "BUSINESS_KEY")
    private String businessKey;

    @Column(name = "PROCESS_CATEGORY")
    private String processCategory;
}
