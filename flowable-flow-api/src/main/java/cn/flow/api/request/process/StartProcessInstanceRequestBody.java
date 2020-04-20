package cn.flow.api.request.process;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Map;

@Data
@ApiModel(value="启动流程传输参数对象",description="启动流程传输参数对象")
public class StartProcessInstanceRequestBody implements Serializable {

    @NotNull(message = "未获取到流程业务Key")
    @ApiModelProperty(value="流程业务Key",name="processBusinessKey", example="流程业务Key")
    private String processBusinessKey;

    @ApiModelProperty(value="暂可不填",name="outcome", example="outcome")
    private String outcome;

    @NotNull(message = "未获取到表单数据")
    @ApiModelProperty(value="表单信息",name="variables", example="片区id",required = true)
    private Map<String, Object> variables;

    @NotNull(message = "未获取到片区ID")
    @ApiModelProperty(value="片区ID",name="processScopeIds",example="片区id集合",required = true)
    private String processScopeId;

    @NotNull(message = "未获取到发起人ID")
    @Size(min = 32, max = 32, message = "发起人ID不合法")
    @ApiModelProperty(value="发起人ID",name="initiator",example="ponta",required = true)
    private String initiator;

    @NotNull(message = "未获取到流程名称")
    @ApiModelProperty(value="流程名称",name="processInstanceName",example="维保流程",required = true)
    private String processInstanceName;
}
