package cn.flow.api.request.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class TaskComment {

    @ApiModelProperty("流程实例id")
    @NotEmpty(message = "未获取到流程实例id")
    protected String processInstanceId;

    @ApiModelProperty("任务id")
    @NotEmpty(message = "未获取到任务id")
    protected String taskId;

    @ApiModelProperty("用户id")
    @NotEmpty(message = "未获取到用户id")
    private String userId;

    @ApiModelProperty("用户名")
    @NotEmpty(message = "未获取到用户名")
    private String userName;

    @ApiModelProperty("评论内容")
    @NotEmpty(message = "未获取到评论内容")
    private String content;
}
