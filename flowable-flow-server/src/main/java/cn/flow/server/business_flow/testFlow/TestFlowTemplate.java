package cn.flow.server.business_flow.testFlow;

import cn.flow.api.enums.TaskStatus;
import cn.flow.component.component.AbstractStartComponent;
import cn.flow.component.component.EndEventComponent;
import cn.flow.component.component.SimpleStartEventComponent;
import cn.flow.component.component.SimpleUserTaskComponent;
import cn.flow.component.constant.Priority;
import cn.flow.component.listener.CustomTaskListener;
import cn.flow.component.template.ExtensionProcess;
import cn.flow.server.business_flow.BaseWorkflowTemplate;
import cn.flow.server.business_flow.testFlow.form.TestStartForm;
import cn.flow.server.business_flow.testFlow.form.TestUserHandForm;
import cn.flow.server.constant.RoleKey;
import org.flowable.task.service.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class TestFlowTemplate extends BaseWorkflowTemplate {

    private static Logger logger = LoggerFactory.getLogger(TestFlowTemplate.class);
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
        SimpleUserTaskComponent simpleComponent1 = simpleUserComponent1();
        EndEventComponent endEventComponent = createEndEventComponent();

        /** 连线 **/
        simpleStartEventComponent.setNextComponent(simpleComponent1);
        simpleComponent1.setNextComponent(endEventComponent);
    }


    /**
     * 人工节点
     */
    public static SimpleUserTaskComponent simpleUserComponent1(){
        return new SimpleUserTaskComponent.Builder()
                //设置流程名称
                .setUserTaskName("测试人工节点")
                .setDocumentation("测试人工节点documentation")
                .setTaskListenerSpringBeanName("testListener")
                .setFormClass(TestUserHandForm.class)
                // 未处理状态
                .setDefaultTaskStatus(TaskStatus.WAITING_PROCESSED.name())
                //添加审批人员角色
                .addCndidateGroup(RoleKey.TEST_ROLE)
                //设置流程中的位置
                .setDisplayIndex(2)
                //是否展示
                .setDisplayable(false)
                //优先级
                .setPriority(Priority.LEVEL9)
                //构建
                .build();
    }

    @Bean("testListener")
    public CustomTaskListener testListener() {
        return new CustomTaskListener() {
            @Override
            public void create(DelegateTask delegateTask) {
                logger.info("测试人工节点 create");
            }
            @Override
            public void complete(DelegateTask delegateTask) {
                logger.info("测试人工节点 complete");
            }
            @Override
            public void delete(DelegateTask delegateTask) {
                logger.info("测试人工节点 delete");
            }
            @Override
            public void assignment(DelegateTask delegateTask) {
                logger.info("测试人工节点 assignment");
            }
        };
    }

}
