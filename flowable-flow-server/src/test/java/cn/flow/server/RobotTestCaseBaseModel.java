package cn.flow.server;

import cn.flow.api.request.task.CompleteTaskRequestBody;
import cn.flow.api.request.task.FindTaskRequestBody;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.api.response.task.EasyTaskInfoResponse;
import cn.flow.api.result.Result;
import cn.flow.component.exception.FlowTaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 自动化测试用例基础方法
 */
@Slf4j
public class RobotTestCaseBaseModel extends WorkFlowBaseTestCase {

    private final static String SYS_USER_ID = "SYS_USER_ID";

    /**
     * 拉起流程
     *
     * @param userId         用户Id
     * @param processKey     流程Key
     * @param processName    流程名
     * @param processScopeId scopeId
     * @param variables      流程参数
     * @return 流程实例ID
     */
    public String startComponent(String userId, String processKey, String processName, String processScopeId, Map<String, Object> variables) {
        Result<ProcessInstanceResponseBody> result = startProcessInstanceWithForm(userId, processKey, processName, processScopeId, variables);
        assertSuccess(result);
        return result.getData().getProcessInstanceId();
    }

    /**
     * 通知流程继续往下走
     *
     * @param processInstanceId 流程实例ID
     * @param outCome           没什么用随便传
     * @param variables         流程参数
     */
    public void keepOnWithForm(String processInstanceId, String taskName, String outCome, Map<String, Object> variables) {
        // 获取待办
        EasyTaskInfoResponse taskInfo = getEasyTaskInfo(processInstanceId, taskName, System.currentTimeMillis());

        //  如果没有找到待办，就说明流程走向出现了问题
        if (taskInfo == null) {
            throw new FlowTaskException("没有找到下一步的待办，看看是不是流程路线走错了");
        }
        CompleteTaskRequestBody completeTaskRequestBody = new CompleteTaskRequestBody(taskInfo.getUserId(), taskInfo.getTaskId(), taskInfo.getTaskFormDefinitionId(), outCome, variables);
        if (StringUtils.isEmpty(completeTaskRequestBody.getUserId())) {
            completeTaskRequestBody.setUserId(SYS_USER_ID);
        }
        Result result = taskApi.completeTaskWithForm(completeTaskRequestBody);
        assertSuccess(result);
    }


    /**
     * 通过任务名查询任务
     */
    private EasyTaskInfoResponse getEasyTaskInfo(String processInstanceId, String taskName, Long times) {
        if (System.currentTimeMillis() - times > 10000) {
            log.error("获取不到待办任务：" + taskName);
            return null;
        }
        EasyTaskInfoResponse taskInfo;
        try {
            FindTaskRequestBody findTaskRequestBody = new FindTaskRequestBody(processInstanceId, taskName);
            Result<EasyTaskInfoResponse> result = taskApi.getTaskByName(findTaskRequestBody);
            assertSuccess(result);
            taskInfo = result.getData();
        } catch (Exception e) {
            return getEasyTaskInfo(processInstanceId, taskName, times);
        }
        if (!Objects.isNull(taskInfo))
            return taskInfo;
        return getEasyTaskInfo(processInstanceId, taskName, times);
    }
}
