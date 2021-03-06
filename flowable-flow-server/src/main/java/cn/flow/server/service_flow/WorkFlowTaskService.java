package cn.flow.server.service_flow;

import cn.flow.api.enums.TaskStatus;
import cn.flow.component.exception.FlowTaskException;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class WorkFlowTaskService {

    @Autowired
    private TaskService taskService;

    /**
     * 通过任务Id查询任务
     */
    public Task getTaskById(String taskId) {
        Task task = taskService.createTaskQuery().includeIdentityLinks().taskId(taskId).singleResult();
        if (Objects.isNull(task)) {
            throw new FlowTaskException(FlowTaskException.TASK_NOT_FOUND_MESSAGE + taskId);
        }
        return task;
    }

    /**
     * 通过任务名查询任务
     */
    public Task getTaskByName(String processInstanceId, String taskName){
        List<Task> taskList = taskService.createTaskQuery()
                .includeIdentityLinks()
                .processInstanceId(processInstanceId)
                .taskName(taskName)
                .orderByTaskCreateTime().desc().list();
        if (CollectionUtils.isEmpty(taskList)){
            throw new FlowTaskException(FlowTaskException.TASK_NOT_FOUND_MESSAGE + taskName);
        }
        return taskList.get(0);
    }

    /**
     * 认领任务
     */
    @Transactional(rollbackFor = Exception.class)
    public void claimTask(String taskId, String userId) {
        Task task = taskService.createTaskQuery()
                .includeProcessVariables()
                .taskId(taskId).singleResult();
        if (Objects.isNull(task)) {
            throw new FlowTaskException(FlowTaskException.TASK_NOT_FOUND_MESSAGE + taskId);
        }

        if (!StringUtils.isEmpty(task.getAssignee())) {
            throw new FlowTaskException("task has been claim");
        }
        // TODO 此处可以接入鉴权
        task.setCategory(TaskStatus.WAITING_CLAIM_PROCESSED.name());
        taskService.saveTask(task);
        taskService.claim(taskId, userId);
    }

    /**
     * 反认领任务
     */
    @Transactional(rollbackFor = Exception.class)
    public void unClaimTask(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId)
                .includeProcessVariables()
                .singleResult();
        if (Objects.isNull(task)) {
            throw new FlowTaskException(FlowTaskException.TASK_NOT_FOUND_MESSAGE + taskId);
        }
        if (StringUtils.isEmpty(task.getAssignee())) {
            throw new FlowTaskException("task has not be claim，could not be claim");
        }
        task.setCategory(TaskStatus.WAITING_CLAIM.name());
        taskService.saveTask(task);
        taskService.unclaim(taskId);
    }

    /**
     * 纯洁的完成任务
     */
    @Transactional
    public void completeTask(String taskId, String userId, Map<String, Object> variables) {
        Task task = getTaskById(taskId);
        // TODO 此处可以接入鉴权
        taskService.setAssignee(task.getId(), userId);
        if (CollectionUtils.isEmpty(variables)){
            taskService.complete(taskId);
        } else {
            taskService.complete(taskId, variables);
        }
    }

    /**
     * 提交表单完成任务
     */
    @Transactional
    public void completeTaskWithForm(String taskId, String userId, String formDefinitionId, String outcome, Map<String, Object> variables){
        Task task = getTaskById(taskId);
        // TODO 此处可以接入鉴权
        taskService.setAssignee(task.getId(), userId);
        taskService.completeTaskWithForm(taskId, formDefinitionId, outcome, variables);
    }

}
