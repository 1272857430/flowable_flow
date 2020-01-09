package cn.flow.component.template;

import cn.flow.component.model.DeploymentEntity;

public interface ITemplate {

    DeploymentEntity buildTestCase(String processKey, Class<?> aClass);

    DeploymentEntity deploy(String processKey);
}
