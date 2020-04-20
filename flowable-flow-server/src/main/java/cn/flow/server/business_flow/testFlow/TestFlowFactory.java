package cn.flow.server.business_flow.testFlow;

import cn.flow.api.enums.TaskStatus;
import cn.flow.component.component.SimpleUserTaskComponent;
import cn.flow.component.constant.Priority;
import cn.flow.server.business_flow.testFlow.form.TestUserHandForm;
import cn.flow.server.constant.RoleKey;

class TestFlowFactory {

    /**
     * 人工节点
     */
    static SimpleUserTaskComponent simpleUserComponent1(){
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
}
