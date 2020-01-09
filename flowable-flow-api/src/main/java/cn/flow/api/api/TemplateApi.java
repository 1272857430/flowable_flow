package cn.flow.api.api;

import cn.flow.api.request.template.DeployRequestBody;
import cn.flow.api.response.template.DeployResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@Api(value = "/workflow/template", description = "流程模版管理")
@FeignClient(value = "flowable-flow")
@RequestMapping("/workflow/template")
public interface TemplateApi {

    @ApiOperation(value = "部署流程")
    @RequestMapping(value = "/deploy",method = RequestMethod.POST)
    DeployResponseBody deploy(@RequestBody DeployRequestBody requestBody);

    @ApiOperation(value = "生成测试用例")
    @RequestMapping(value = "/createTestCase",method = RequestMethod.POST)
    DeployResponseBody createTestCase(@RequestParam("processKey") String processKey);
}
