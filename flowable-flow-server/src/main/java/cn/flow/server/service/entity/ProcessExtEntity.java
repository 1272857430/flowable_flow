package cn.flow.server.service.entity;

import cn.flow.server.utils.DateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Getter
@Setter
@Entity
@Table(name = "EXT_RU_PROINS")
public class ProcessExtEntity extends BaseEntityBean{

    @Column(name = "PROCESS_INSTANCE_ID")
    private String processInstanceId;

    @Column(name = "PROCESS_SCOPE_ID")
    private String processScopeId;

    @Column(name = "PROCESS_NUMBER")
    private String processNumber;

    @Column(name = "BUSINESS_KEY")
    private String businessKey;

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

    @Column(name = "PROCESS_CATEGORY")
    private String processCategory;

    public ProcessExtEntity() {
    }

    public ProcessExtEntity(ProcessInstance processInstance, String processScopeId, String startUserName, String status) {
        this.processInstanceId = processInstance.getProcessInstanceId();
        this.processScopeId = processScopeId;
        int randomInt = (int) (Math.random() * 100);
        this.processNumber =  DateUtil.formatDate(new Date(), DateUtil.FORMAT_YYYYMMDDHHMMSS) + randomInt;
        this.startTime = processInstance.getStartTime();
//        this.endTime = endTime;
        this.startUserId = processInstance.getStartUserId();
        this.startUserName = startUserName;
        this.status = status;
        this.description = processInstance.getDescription();
        this.processInstanceName = processInstance.getName();
        this.processDefinitionId = processInstance.getProcessDefinitionId();
        this.processDefinitionName = processInstance.getProcessDefinitionName();
        this.processDefinitionKey = processInstance.getProcessDefinitionKey();
        this.processDefinitionVersion = processInstance.getProcessDefinitionVersion();
        this.deploymentId =  processInstance.getDeploymentId();
        this.businessKey = processInstance.getBusinessKey();
//        this.processCategory = processCategory;
    }
}
