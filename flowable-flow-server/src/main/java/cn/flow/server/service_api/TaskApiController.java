package cn.flow.server.service_api;

import cn.flow.api.api.TaskApi;
import cn.flow.api.request.task.*;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.task.EasyTaskInfoResponse;
import cn.flow.api.response.task.TaskResponseBody;
import cn.flow.api.result.Result;
import cn.flow.api.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workflow/task")
public class TaskApiController implements TaskApi {

    @Autowired
    private TaskApiService taskApiService;

    /**
     * 认领任务
     */
    @Override
    @RequestMapping(value = "/claim",method = RequestMethod.POST)
    public Result claim(@RequestBody ClaimTaskRequestBody requestBody) {
        if (StringUtils.isEmpty(requestBody.getTaskId()) || StringUtils.isEmpty(requestBody.getUserId())){
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        taskApiService.claim(requestBody);
        return new Result<>();
    }

    /**
     * 反认领任务
     */
    @Override
    @RequestMapping(value = "/unClaim",method = RequestMethod.POST)
    public Result unClaim(@RequestBody ClaimTaskRequestBody requestBody) {
        if (StringUtils.isEmpty(requestBody.getTaskId()) || StringUtils.isEmpty(requestBody.getUserId())){
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        taskApiService.unClaim(requestBody);
        return new Result<>();
    }

    /**
     * 单纯的完成任务
     */
    @Override
    @RequestMapping(value = "/completeTask",method = RequestMethod.POST)
    public Result completeTask(@RequestBody CompleteTaskRequestBody requestBody) {
        if (StringUtils.isEmpty(requestBody.getTaskId()) || StringUtils.isEmpty(requestBody.getUserId())){
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        return taskApiService.completeTask(requestBody);
    }

    /**
     * 正常完成任务
     */
    @Override
    @RequestMapping(value = "/completeTaskWithForm",method = RequestMethod.POST)
    public Result completeTaskWithForm(@RequestBody CompleteTaskRequestBody requestBody) {
        if (StringUtils.isEmpty(requestBody.getTaskId())
                || StringUtils.isEmpty(requestBody.getUserId())
                || StringUtils.isEmpty(requestBody.getFormDefinitionId())
                || CollectionUtils.isEmpty(requestBody.getVariables())){
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        return taskApiService.completeTaskWithForm(requestBody);
    }

    /**
     * 查询某个任务详情
     */
    @Override
    @RequestMapping(value = "/getSingleTaskInfo/{taskId}", method = RequestMethod.GET)
    public Result<TaskResponseBody> getSingleTaskInfo(@PathVariable("taskId") String taskId) {
        return null;
    }

    /**
     * 查询待办任务
     */
    @Override
    @RequestMapping(value = "/getPendingTask",method = RequestMethod.POST)
    public Result<List<TaskResponseBody>> getPendingTask(@RequestBody GetPendingTaskRequestBody requestBody) {
        return null;
    }

    @Override
    @RequestMapping(value = "/getProcessedFormInfo",method = RequestMethod.POST)
    public Result<List<FormModelResponseBody>> getProcessedFormInfo(@RequestBody GetProcessedFormInfoRequestBody requestBody) {
        return null;
    }

    @Override
    @RequestMapping(value = "/getCurrentTaskFormModel",method = RequestMethod.POST)
    public Result<List<FormModelResponseBody>> getCurrentTaskFormModel(@RequestBody GetCurrentTaskFormModelRequestBody requestBody) {
        return null;
    }

    /**
     * 通过流程ID和任务名查询任务
     */
    @Override
    @RequestMapping(value = "/getTaskByName", method = RequestMethod.POST)
    public Result<EasyTaskInfoResponse> getTaskByName(@RequestBody FindTaskRequestBody requestBody) {
        if (StringUtils.isEmpty(requestBody.getProcessInsId()) || StringUtils.isEmpty(requestBody.getTaskName())){
            return new Result<>(ResultCode.PARAM_ERROR);
        }
        return taskApiService.getTaskByName(requestBody);
    }
}
