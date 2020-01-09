package cn.flow.component.component;

import cn.flow.component.form.TranslateFieldAnnotation;
import cn.flow.component.utils.BuildExtensionElement;
import cn.flow.component.utils.IdUtils;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.TaskListener;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractUserComponent extends AbstractComponent {

    public static final String SUFFIX_CLAIM_LISTENER_NAME = "-claim-task-name";
    protected UserTask userTask;
    private Class formClass;
    private Integer displayIndex;
    private Boolean displayable = false;
    private String taskListenerSpringBeanName;

    @Override
    public void init() {
        userTask = new UserTask();
        userTask.setId(IdUtils.nextId());
        userTask.setName("用户任务");
    }

    public void addCndidateGroup(String groupId){
        List<String> candidateGroups = userTask.getCandidateGroups();
        if (candidateGroups !=null) {
            candidateGroups.add(groupId);
        }else{
            candidateGroups = new ArrayList<>();
            candidateGroups.add(groupId);
            userTask.setCandidateGroups(candidateGroups);
        }
    }

    public void addCandidateUser(String userId){
        List<String> candidateUsers = userTask.getCandidateUsers();
        if (candidateUsers !=null) {
            candidateUsers.add(userId);
        }else{
            candidateUsers = new ArrayList<>();
            candidateUsers.add(userId);
            userTask.setCandidateUsers(candidateUsers);
        }
    }

    public Class getFormClass() {
        return formClass;
    }

    public void setFormClass(Class tClass){
        this.formClass = tClass;
        this.userTask.setFormKey(TranslateFieldAnnotation.getFormKey(tClass));
    }

    public UserTask getUserTask() {
        return userTask;
    }

    public Integer getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(Integer displayIndex) {
        this.displayIndex = displayIndex;
    }

    public Boolean getDisplayable() {
        return displayable;
    }

    public void setDisplayable(Boolean displayable) {
        this.displayable = displayable;
    }

    @Override
    public String getTaskListenerSpringBeanName() {
        return taskListenerSpringBeanName;
    }

    @Override
    public void setTaskListenerSpringBeanName(String taskListenerSpringBeanName) {
        this.taskListenerSpringBeanName = taskListenerSpringBeanName;
    }

    @Override
    public void buildExtensionElements() {
        if(!StringUtils.isEmpty(taskListenerSpringBeanName)){
            ExtensionElement element = BuildExtensionElement.buildTaskListenerElement(TaskListener.EVENTNAME_ALL_EVENTS, getTaskListenerSpringBeanName());
            getSelfFlowNode().addExtensionElement(element);
        }
    }
}
