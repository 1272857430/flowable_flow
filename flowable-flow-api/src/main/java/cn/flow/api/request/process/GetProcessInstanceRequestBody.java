package cn.flow.api.request.process;

import cn.flow.api.request.PageRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "我已完成的任务")
public class GetProcessInstanceRequestBody extends PageRequestBody {
    private String userId;
    @ApiModelProperty(value = "开始时间")
    private Long startTime;
    @ApiModelProperty(value = "结束时间")
    private Long endTime;
    @ApiModelProperty(value = "状态")
    private String status;
    @ApiModelProperty(value = "搜索关键字")
    private String keyword;
    @ApiModelProperty(value = "数据权限，片区ID")
    private List<String> processScopeIds;
    @ApiModelProperty(value = "发起人姓名")
    private String startUserName;
    @ApiModelProperty(value = "流程定义key集合")
    private List<String> processDefinitionKeys;

}
