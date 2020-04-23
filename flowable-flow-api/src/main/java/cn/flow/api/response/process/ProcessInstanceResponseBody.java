package cn.flow.api.response.process;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@ApiModel(value="流程实例对象",description="流程实例对象")
public class ProcessInstanceResponseBody {

    @ApiModelProperty(value="流程实例ID",name="processInstanceId")
    private String processInstanceId;

    @ApiModelProperty(value="流程实例名称",name="processInstanceName")
    private String processInstanceName;

    private String processDefinitionId;

    private String processDefinitionKey;

    private String processDefinitionName;

    private Integer processDefinitionVersion;

    @ApiModelProperty(value="描述",name="description")
    private String description;

    @ApiModelProperty(value="发起人id",name="startUserId")
    private String startUserId;

    @ApiModelProperty(value="发起人姓名",name="startUserName")
    private String startUserName;

    @ApiModelProperty(value="开始时间",name="startTime")
    private Date startTime;

    @ApiModelProperty(value="结束时间",name="endTime")
    private Date endTime;

    @ApiModelProperty(value="流程状态，1：已完成，2：未完成",name="status")
    private String status;

    private Date lockTime;

    @ApiModelProperty(value="片区ID",name="processScopeId")
    private String processScopeId;

    @ApiModelProperty(value="流程编号",name="processNumber")
    private String processNumber;

    @ApiModelProperty(value="流程耗时",name="durationInMillis")
    private Long durationInMillis;

    @ApiModelProperty(value="业务ID",name="businessKey")
    private String businessKey;

    @ApiModelProperty(value="正在进行的任务名称",name="businessKey")
    private String taskName;

    @ApiModelProperty(value = "处理人")
    private String assignee;

    private String taskId;

    public ProcessInstanceResponseBody() {
    }
}
