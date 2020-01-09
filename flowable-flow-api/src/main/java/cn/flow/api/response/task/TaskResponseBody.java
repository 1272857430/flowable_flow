package cn.flow.api.response.task;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@ApiModel(value = "待办任务Body")
public class TaskResponseBody {
    @ApiModelProperty(value = "任务ID")
    private String taskId;
    @ApiModelProperty(value = "任务名称")
    private String taskName;
    @ApiModelProperty(value = "任务归属人")
    private String owner;
    @ApiModelProperty(value = "处理人")
    private String assignee;
    @ApiModelProperty(value = "任务描述")
    private String description;
    @ApiModelProperty(value = "优先级")
    private Integer priority;
    @ApiModelProperty(value = "任务创建时间")
    private Date startTime;
    @ApiModelProperty(value = "任务到期时间")
    private Date dueDate;
    @ApiModelProperty(value = "任务类型")
    private String category;
    @ApiModelProperty(value = "执行ID")
    private String executionId;
    @ApiModelProperty(value = "流程实例ID")
    private String processInstanceId;
    @ApiModelProperty(value = "流程定义ID")
    private String processDefinitionId;
    @ApiModelProperty(value = "流程定义名称")
    private String processDefinitionName;
    @ApiModelProperty(value = "发起人")
    private String startUserId;
    @ApiModelProperty(value = "发起人名称")
    private String startUserName;
    @ApiModelProperty(value = "流程实例名称")
    private String processInstanceName;
    @ApiModelProperty(value = "流程定义Key")
    private String processDefinitionKey;
    @ApiModelProperty(value = "任务定义ID")
    private String taskDefinitionId;
    @ApiModelProperty(value = "任务定义Key")
    private String taskDefinitionKey;
    @ApiModelProperty(value = "表单Key")
    private String formKey;
    @ApiModelProperty(value = "任务领取时间")
    private Date claimTime;
    @ApiModelProperty(value = "任务类型")
    private String taskType;
    @ApiModelProperty(value = "任务状态")
    private String taskStatus;
    @ApiModelProperty(value = "流程编号")
    private String processNumber;
    @ApiModelProperty(value = "片区ID")
    private String processScopeId;
}
