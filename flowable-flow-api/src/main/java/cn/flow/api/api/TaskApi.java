package cn.flow.api.api;

import cn.flow.api.request.task.*;
import cn.flow.api.response.form.FormModelResponseBody;
import cn.flow.api.response.task.ClaimTaskResponseBody;
import cn.flow.api.response.task.CompleteTaskResponseBody;
import cn.flow.api.response.task.EasyTaskInfoResponse;
import cn.flow.api.response.task.TaskResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "/workflow/task", description = "任务管理")
@FeignClient(value = "flowable-flow")
@RequestMapping("/workflow/task")
public interface TaskApi {

    @ApiOperation(value = "查询某个任务详情")
    @RequestMapping(value = "/getSingleTaskInfo/{taskId}", method = RequestMethod.GET)
    TaskResponseBody getSingleTaskInfo(@PathVariable("taskId") String taskId);

    @ApiOperation(value = "查询待办任务")
    @RequestMapping(value = "/getPendingTask",method = RequestMethod.POST)
    List<TaskResponseBody> getPendingTask(@RequestBody GetPendingTaskRequestBody requestBody);

    @ApiOperation(value = "单纯的完成任务")
    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    CompleteTaskResponseBody complete(CompleteTaskRequestBody requestBody);

    @ApiOperation(value = "正常完成任务")
    @RequestMapping(value = "/completeTaskWithForm",method = RequestMethod.POST)
    CompleteTaskResponseBody completeTaskWithForm(@RequestBody CompleteTaskRequestBody requestBody);

    @ApiOperation(value = "认领任务")
    @RequestMapping(value = "/claim",method = RequestMethod.POST)
    ClaimTaskResponseBody claim(@RequestBody ClaimTaskRequestBody requestBody);

    @ApiOperation(value = "反认领任务")
    @RequestMapping(value = "/unclaim",method = RequestMethod.POST)
    ClaimTaskResponseBody unclaim(@RequestBody ClaimTaskRequestBody requestBody);

    @RequestMapping(value = "/getProcessedFormInfo",method = RequestMethod.POST)
    List<FormModelResponseBody> getProcessedFormInfo(@RequestBody GetProcessedFormInfoRequestBody requestBody);

    @RequestMapping(value = "/getCurrentTaskFormModel",method = RequestMethod.POST)
    List<FormModelResponseBody> getCurrentTaskFormModel(@RequestBody GetCurrentTaskFormModelRequestBody requestBody);

    @RequestMapping(value = "/getTask", method = RequestMethod.POST)
    EasyTaskInfoResponse getTask(@RequestBody FindTaskRequestBody findTaskRequestBody);

    @ApiOperation(value = "添加批注 评论信息")
    @PostMapping("/addComment")
    Object addComment(@RequestBody @Valid TaskComment taskComment);

}
