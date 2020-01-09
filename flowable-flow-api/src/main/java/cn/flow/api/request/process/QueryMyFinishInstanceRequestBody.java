package cn.flow.api.request.process;

import cn.flow.api.request.PageRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "我已处理的流程")
public class QueryMyFinishInstanceRequestBody extends PageRequestBody {
    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "开始时间")
    private Long startTime;
    @ApiModelProperty(value = "结束时间")
    private Long endTime;
    @ApiModelProperty(value = "搜索关键字")
    private String keyword;
    @ApiModelProperty(value = "流程状态")
    private String status;
}
