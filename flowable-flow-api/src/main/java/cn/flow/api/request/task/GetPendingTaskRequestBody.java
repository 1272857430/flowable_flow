package cn.flow.api.request.task;

import cn.flow.api.request.PageRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "获取待办列表请求Body")
public class GetPendingTaskRequestBody extends PageRequestBody {

    @ApiModelProperty(value = "用户ID",required = true)
    private String userId;

    @ApiModelProperty(value = "发起人")
    private String startUserId;

    @ApiModelProperty(value = "发起人姓名")
    private String startUserName;

    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
    private Long endTime;

    @ApiModelProperty(value = "数据权限")
    private List<String> processScopeIds;

    @ApiModelProperty(value = "流程类型")
    private String processType;

    @ApiModelProperty(value = "任务类型")
    private String taskType;

    @ApiModelProperty(value = "任务状态")
    private String status;

    @ApiModelProperty(value = "搜索关键字")
    private String keyword;

    @ApiModelProperty(value = "流程定义key")
    private List<String> processDefinitionKeys;
}
