package cn.flow.api.api;

import cn.flow.api.request.task.*;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.task.EasyTaskInfoResponse;
import cn.flow.api.response.task.TaskResponseBody;
import cn.flow.api.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "/workflow/task", description = "任务管理")
@FeignClient(value = "flowable-flow")
@RequestMapping("/workflow/task")
public interface TaskApi {

    @ApiOperation(value = "认领任务")
    @RequestMapping(value = "/claim",method = RequestMethod.POST)
    Result claim(@RequestBody ClaimTaskRequestBody requestBody);

    @ApiOperation(value = "反认领任务")
    @RequestMapping(value = "/unClaim",method = RequestMethod.POST)
    Result unClaim(@RequestBody ClaimTaskRequestBody requestBody);

    @ApiOperation(value = "单纯的完成任务")
    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    Result complete(CompleteTaskRequestBody requestBody);

    @ApiOperation(value = "正常完成任务")
    @RequestMapping(value = "/completeTaskWithForm",method = RequestMethod.POST)
    Result completeTaskWithForm(@RequestBody CompleteTaskRequestBody requestBody);

    @ApiOperation(value = "查询某个任务详情")
    @RequestMapping(value = "/getSingleTaskInfo/{taskId}", method = RequestMethod.GET)
    Result<TaskResponseBody> getSingleTaskInfo(@PathVariable("taskId") String taskId);

    @ApiOperation(value = "查询待办任务")
    @RequestMapping(value = "/getPendingTask",method = RequestMethod.POST)
    Result<List<TaskResponseBody>> getPendingTask(@RequestBody GetPendingTaskRequestBody requestBody);

    @RequestMapping(value = "/getProcessedFormInfo",method = RequestMethod.POST)
    Result<List<FormModelResponseBody>> getProcessedFormInfo(@RequestBody GetProcessedFormInfoRequestBody requestBody);

    @RequestMapping(value = "/getCurrentTaskFormModel",method = RequestMethod.POST)
    Result<List<FormModelResponseBody>> getCurrentTaskFormModel(@RequestBody GetCurrentTaskFormModelRequestBody requestBody);

    @ApiOperation(value = "通过流程ID和任务名查询任务")
    @RequestMapping(value = "/getTaskByName", method = RequestMethod.POST)
    Result<EasyTaskInfoResponse> getTaskByName(@RequestBody FindTaskRequestBody findTaskRequestBody);

}
