package cn.flow.api.request.process;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@ApiModel(value="启动流程传输参数对象",description="启动流程传输参数对象")
public class StartProcessInstanceRequestBody implements Serializable {

    @ApiModelProperty(value="流程业务Key",name="processBusinessKey", example="片区id")
    private String processBusinessKey;

    @ApiModelProperty(value="暂可不填",name="outcome", example="outcome")
    private String outcome;

    @ApiModelProperty(value="表单信息",name="variables", example="片区id",required = true)
    private Map<String, Object> variables;

    @ApiModelProperty(value="片区ID",name="processScopeIds",example="片区id集合",required = true)
    private String processScopeId;

    @ApiModelProperty(value="发起人ID",name="initiator",example="ponta",required = true)
    private String initiator;

    @ApiModelProperty(value="流程名称",name="processInstanceName",example="维保流程",required = true)
    private String processInstanceName;
}
