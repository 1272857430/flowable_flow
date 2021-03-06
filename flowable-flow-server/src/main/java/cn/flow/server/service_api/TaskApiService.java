package cn.flow.server.service_api;

import cn.flow.api.request.task.*;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.task.EasyTaskInfoResponse;
import cn.flow.api.response.task.TaskResponseBody;
import cn.flow.api.result.Result;
import cn.flow.server.service_flow.WorkFlowFormService;
import cn.flow.server.service_flow.WorkFlowTaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class TaskApiService {

    @Autowired
    private WorkFlowTaskService workFlowTaskService;

    @Autowired
    private WorkFlowFormService flowFormService;

    /**
     * 认领任务
     */
    void claim(ClaimTaskRequestBody requestBody) {
        workFlowTaskService.claimTask(requestBody.getTaskId(), requestBody.getUserId());
    }

    /**
     * 反认领任务
     */
    void unClaim(ClaimTaskRequestBody requestBody) {
        workFlowTaskService.unClaimTask(requestBody.getTaskId());
    }

    /**
     * 单纯的完成任务
     */
    Result completeTask(CompleteTaskRequestBody requestBody) {
        workFlowTaskService.completeTask(requestBody.getTaskId(), requestBody.getUserId(), requestBody.getVariables());
        return new Result();
    }

    /**
     * 正常完成任务
     */
    Result completeTaskWithForm(CompleteTaskRequestBody requestBody) {
        workFlowTaskService.completeTaskWithForm(requestBody.getTaskId(), requestBody.getUserId(), requestBody.getFormDefinitionId(),
                requestBody.getFormDefinitionId(), requestBody.getVariables());
        return new Result();
    }

    /**
     * 查询某个任务详情
     */
    public Result<TaskResponseBody> getSingleTaskInfo(String taskId) {
        return null;
    }

    /**
     * 查询待办任务
     */
    public Result<List<TaskResponseBody>> getPendingTask(GetPendingTaskRequestBody requestBody) {
        return null;
    }

    public Result<List<FormModelResponseBody>> getProcessedFormInfo(GetProcessedFormInfoRequestBody requestBody) {
        return null;
    }

    public Result<List<FormModelResponseBody>> getCurrentTaskFormModel(GetCurrentTaskFormModelRequestBody requestBody) {
        return null;
    }

    /**
     * 通过流程ID和任务名查询任务
     */
    Result<EasyTaskInfoResponse> getTaskByName(FindTaskRequestBody requestBody) {
        Task task = workFlowTaskService.getTaskByName(requestBody.getProcessInsId(), requestBody.getTaskName());
        String taskFormDefinitionId = flowFormService.getFormModelByKey(task.getFormKey()).getFormDefinitionId();
        EasyTaskInfoResponse taskEasyInfo = new EasyTaskInfoResponse(task.getId(), taskFormDefinitionId, task.getAssignee());
        if (StringUtils.isEmpty(taskEasyInfo.getUserId()) && !CollectionUtils.isEmpty(task.getIdentityLinks())) {
            taskEasyInfo.setRoleKey(task.getIdentityLinks().get(0).getGroupId());
        }
        return new Result<>(taskEasyInfo);
    }

}
