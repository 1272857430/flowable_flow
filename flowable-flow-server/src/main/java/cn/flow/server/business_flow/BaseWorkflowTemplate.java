package cn.flow.server.business_flow;

import cn.flow.component.component.AbstractStartComponent;
import cn.flow.component.component.AbstractUserComponent;
import cn.flow.component.component.EndEventComponent;
import cn.flow.component.component.IComponent;
import cn.flow.component.form.deploy.FromDeployService;
import cn.flow.component.taskShow.service.ProcessDefinitionTaskShowConfigService;
import cn.flow.component.template.AbstractTemplate;
import cn.flow.component.utils.SpringContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;

public abstract class BaseWorkflowTemplate extends AbstractTemplate {

    private static Logger logger = LoggerFactory.getLogger(BaseWorkflowTemplate.class);

    @Override
    protected void deployForm(AbstractStartComponent startEventComponent) {
        recursionDeployForm(startEventComponent);
    }

    @Override
    protected void createTestCase(String processKey, String processName, Class<?> aClass, AbstractStartComponent startEventComponent, String modelPath, String classPath) {

    }

    @Override
    protected void saveDefinitionTaskShow(String processKey, IComponent component) {
        component.setIsSaveDisplay(true);
        if(component instanceof AbstractUserComponent){
            AbstractUserComponent abstractUserTaskComponent = (AbstractUserComponent) component;
            ProcessDefinitionTaskShowConfigService definitionTaskShowConfigService = SpringContextUtils.getBean(ProcessDefinitionTaskShowConfigService.class);
            if (!Objects.isNull(definitionTaskShowConfigService) &&
                    !Objects.isNull(abstractUserTaskComponent.getDisplayIndex()) &&
                    !Objects.isNull(abstractUserTaskComponent.getDisplayable())){
                definitionTaskShowConfigService.insertProcessDefinitionTaskShow(processKey, abstractUserTaskComponent.getUserTask().getId(), abstractUserTaskComponent.getDisplayIndex(), abstractUserTaskComponent.getDisplayable());
            }
        }
        Map<Integer, IComponent> components = component.getNextComponents();
        for (Integer key : components.keySet()) {
            IComponent nextComponent = components.get(key);
            if(nextComponent != null){
                if(nextComponent.isSaveDisplay()){
                    continue;
                }
                saveDefinitionTaskShow(processKey, nextComponent);
            }else{
                logger.error("Component objects cannot be null");
            }
        }
    }

    /**
     * 表单部署
     */
    private void recursionDeployForm(IComponent component) {

        if (component == null || (component.isBuildForm() != null && component.isBuildForm()))
            return;

        component.setIsBuildForm(true);

        if(component instanceof AbstractUserComponent) {
            fromDeployService.deployFrom(((AbstractUserComponent) component).getFormClass());
        }

        if (component instanceof AbstractStartComponent) {
            fromDeployService.deployFrom(((AbstractStartComponent) component).getFormClass());
        }

        if (CollectionUtils.isEmpty(component.getNextComponents()))
            return;

        component.getNextComponents().forEach((key, comp) -> {
            if (comp != null && !comp.isBuildComponent()) {
                recursionDeployForm(comp);
            }
        });
    }

    public static EndEventComponent createEndEventComponent(){
        return new EndEventComponent.Builder().build();
    }
}
