package cn.flow.server.rest;

import cn.flow.api.api.TemplateApi;
import cn.flow.api.request.template.DeployRequestBody;
import cn.flow.api.response.template.DeployResponseBody;
import cn.flow.api.result.Result;
import cn.flow.component.form.deploy.FromDeployService;
import cn.flow.component.model.DeploymentEntity;
import cn.flow.component.template.AbstractTemplate;
import cn.flow.server.utils.SpringContextUtil;
import org.flowable.form.api.FormDeployment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/workflow/template")
public class TemplateController implements TemplateApi {

    @Autowired
    private FromDeployService fromDeployService;

    @Override
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public Result<DeployResponseBody> deploy(@RequestBody DeployRequestBody requestBody) {
        DeployResponseBody responseBody = new DeployResponseBody();
        AbstractTemplate bean = getTemplate(requestBody.getTemplateClassName());
        DeploymentEntity deploy = bean.deploy(requestBody.getTemplateClassName());
        BeanUtils.copyProperties(deploy, responseBody);
        return new Result<>(responseBody);
    }

    @Override
    @RequestMapping(value = "/createTestCase", method = RequestMethod.POST)
    public Result<DeployResponseBody> createTestCase(@RequestBody DeployRequestBody requestBody) {
        DeployResponseBody responseBody = new DeployResponseBody();
        AbstractTemplate bean = getTemplate(requestBody.getTemplateClassName());
        DeploymentEntity deploy = bean.buildTestCase(requestBody.getProcessKey(), bean.getClass());
        BeanUtils.copyProperties(deploy, responseBody);
        return new Result<>(responseBody);
    }

    @Override
    @RequestMapping(value = "/deployFormModel", method = RequestMethod.POST)
    public Result<FormDeployment> deployFormModel(@RequestBody DeployRequestBody requestBody) {
        Class<?> formClass = getClassByName(requestBody.getFormClassName());
        FormDeployment formDeployment = fromDeployService.deployFrom(formClass);
        return new Result<>(formDeployment);
    }

    private static AbstractTemplate getTemplate(String templateClassName) {
        Class<?> aClass = getClassByName(templateClassName);
        return (AbstractTemplate) SpringContextUtil.getBean(aClass);
    }

    private static Class<?> getClassByName(String className) {
        if (StringUtils.isEmpty(className))
            throw new RuntimeException("className is null");
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("className is error");
        }
        if (Objects.isNull(aClass)) {
            throw new RuntimeException("class is null");
        }
        return aClass;
    }
}
