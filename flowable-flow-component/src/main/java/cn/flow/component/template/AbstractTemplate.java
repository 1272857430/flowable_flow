package cn.flow.component.template;

import cn.flow.component.component.AbstractStartComponent;
import cn.flow.component.component.IComponent;
import cn.flow.component.constant.DeployFilePath;
import cn.flow.component.form.deploy.FromDeployService;
import cn.flow.component.model.DeploymentEntity;
import cn.flow.component.utils.FileUtils;
import cn.flow.component.utils.IdUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractTemplate implements ITemplate {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTemplate.class);

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected FromDeployService fromDeployService;

    /**
     * 构建启动组件
     */
    protected abstract AbstractStartComponent buildStartEventComponent();

    /**
     * 构建流程信息
     */
    protected abstract ExtensionProcess buildExtensionProcess();

    /**
     * 构建组件连接
     */
    protected abstract void buildComponents(AbstractStartComponent component);

    /**
     * 部署表单
     */
    protected abstract void deployForm(AbstractStartComponent startEventComponent);

    /**
     * 保存流程任务定义展示信息
     */
    protected abstract void saveDefinitionTaskShow(String processKey, IComponent component);

    /**
     * 生成测试用例
     */
    protected abstract void createTestCase(String processKey, String processName, Class<?> aClass, AbstractStartComponent startEventComponent, String modelPath, String classPath);


    /**
     * 生成测试用例
     */
    @Override
    public DeploymentEntity buildTestCase(String processKey, Class<?> aClass) {
        logger.info("==========生成测试用例使用=========");
        DeploymentEntity deploymentEntity = new DeploymentEntity();
        ExtensionProcess process = buildExtensionProcess();
        AbstractStartComponent startEventComponent = buildStartEventComponent();
        buildComponents(startEventComponent);
        BpmnModel bpmnModel = process.buildBpmnModel(startEventComponent, process);
        List<ValidationError> validationErrors = bpmnModelCheck(bpmnModel);
        // 验证
        validationErrors(deploymentEntity, validationErrors);
        if (deploymentEntity.isHasError()) {
            deploymentEntity.setOk(false);
            return deploymentEntity;
        }
        // 构建没有出错，生成测试用例
        logger.info("==========构建没有出错，生成测试用例=========");
        createTestCase(processKey, bpmnModel.getProcesses().get(0).getName(), aClass, startEventComponent, DeployFilePath.templateFileAbsolutePath, DeployFilePath.classFileAbsolutePath);
        return deploymentEntity;
    }

    /**
     * 发布流程
     */
    @Override
    public DeploymentEntity deploy(String processKey) {
        DeploymentEntity deploymentEntity = new DeploymentEntity();

        ExtensionProcess process = buildExtensionProcess();
        AbstractStartComponent startEventComponent = buildStartEventComponent();
        buildComponents(startEventComponent);
        // 构建完毕，部署表单
        deployForm(startEventComponent);
        BpmnModel bpmnModel = process.buildBpmnModel(startEventComponent, process);
        List<ValidationError> validationErrors = bpmnModelCheck(bpmnModel);
        // 验证
        validationErrors(deploymentEntity, validationErrors);
        String xml = convertToXML(bpmnModel);
        deploymentEntity.setXml(xml);
        FileUtils.appendLog(process.getId(), xml, DeployFilePath.xmlAbsolutePath + "/");
        logger.debug(xml);

        if (deploymentEntity.isHasError()) {
            deploymentEntity.setOk(false);
            return deploymentEntity;
        }

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .name(getClass().getSimpleName()).key(IdUtils.nextId())
                .addBpmnModel(process.getId()+".bpmn20.xml", bpmnModel);

        Deployment deploy = deploymentBuilder.deploy();

        if (deploy == null) {
            deploymentEntity.setErrorMessage("deployment object is null");
            deploymentEntity.setOk(false);
            return deploymentEntity;
        }

        BeanUtils.copyProperties(deploy,deploymentEntity);
        // 保存流程任务定义展示信息
        saveDefinitionTaskShow(processKey, startEventComponent);
        return deploymentEntity;
    }


    private void validationErrors(DeploymentEntity deploymentEntity, List<ValidationError> validationErrors) {
        StringBuilder errorMsg = new StringBuilder();
        if(validationErrors != null && !validationErrors.isEmpty()){
            for (ValidationError error:validationErrors){
                errorMsg.append(error.getProblem()).append("\n");
                logger.error(error.getProblem());
                deploymentEntity.setHasError(true);
            }
            deploymentEntity.setErrorMessage(errorMsg.toString());
        }
    }

    private String convertToXML(BpmnModel bpmnModel){
        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        return new String(bpmnXMLConverter.convertToXML(bpmnModel, "UTF-8"));
    }

    private List<ValidationError> bpmnModelCheck(BpmnModel bpmnModel){
        ProcessValidatorFactory processValidatorFactory = new ProcessValidatorFactory();
        ProcessValidator defaultProcessValidator = processValidatorFactory.createDefaultProcessValidator();
        return defaultProcessValidator.validate(bpmnModel);
    }
}
