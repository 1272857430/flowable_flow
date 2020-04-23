package cn.flow.server.business_flow.testFlow;

import cn.flow.component.component.AbstractStartComponent;
import cn.flow.component.component.EndEventComponent;
import cn.flow.component.component.SimpleStartEventComponent;
import cn.flow.component.component.SimpleUserTaskComponent;
import cn.flow.component.template.ExtensionProcess;
import cn.flow.server.business_flow.BaseWorkflowTemplate;
import cn.flow.server.business_flow.testFlow.form.TestStartForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TestFlowTemplate extends BaseWorkflowTemplate {

    public static final String PROCESS_KEY = "process-test-flow";

    @Override
    protected AbstractStartComponent buildStartEventComponent() {
        return new SimpleStartEventComponent.Builder()
                .setComponentName("测试流程发起节点")
                .setFormClass(TestStartForm.class)
                .setStartEventName("测试流程发起节点").build();
    }

    @Override
    protected ExtensionProcess buildExtensionProcess() {
        return new ExtensionProcess.Builder()
                .setProcessKey(PROCESS_KEY)
                .setProcessName("测试流程")
                .setDocumentation("测试流程")
                .build();
    }

    @Override
    protected void buildComponents(AbstractStartComponent component) {

        SimpleStartEventComponent simpleStartEventComponent = (SimpleStartEventComponent) component;
        SimpleUserTaskComponent simpleComponent1 = TestFlowFactory.simpleUserComponent1();
        EndEventComponent endEventComponent = createEndEventComponent();

        /* 连线 */
        simpleStartEventComponent.setNextComponent(simpleComponent1);

        simpleComponent1.setNextComponent(endEventComponent);
        simpleComponent1.setTaskListenerSpringBeanName("testListener");
    }
}
