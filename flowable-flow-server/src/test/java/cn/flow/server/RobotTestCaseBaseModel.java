package cn.flow.server;

import cn.flow.api.request.task.CompleteTaskRequestBody;
import cn.flow.api.request.task.FindTaskRequestBody;
import cn.flow.api.response.process.ProcessInstanceResponseBody;
import cn.flow.api.response.task.CompleteTaskResponseBody;
import cn.flow.api.response.task.EasyTaskInfoResponse;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 自动化测试用例基础方法
 */
public class RobotTestCaseBaseModel extends WorkFlowBaseTestCase {

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
        ProcessInstanceResponseBody processInstanceResponseBodyResult = startProcessInstanceWithForm(userId, processKey, processName, processScopeId, variables);
        assertSuccess(processInstanceResponseBodyResult);
        printJsonString(processInstanceResponseBodyResult);
        return processInstanceResponseBodyResult.getProcessInstanceId();
    }


    /**
     * 通知流程继续往下走
     *
     * @param processInstanceId 流程实例ID
     * @param outCome           没什么用随便传
     * @param variables         流程参数
     */
    public CompleteTaskResponseBody keepOnWithForm(String processInstanceId, String taskName, String outCome, Map<String, Object> variables) {
        // 获取待办
        EasyTaskInfoResponse easyTaskInfoResponse = getEasyTaskInfo(processInstanceId, taskName, System.currentTimeMillis());

        //  如果没有找到待办，就说明流程走向出现了问题
        if (easyTaskInfoResponse == null) {
            logger.error("没有找到下一步的待办，看看是不是流程路线走错了");
        }

        CompleteTaskRequestBody completeTaskRequestBody = new CompleteTaskRequestBody();
        completeTaskRequestBody.setTaskId(easyTaskInfoResponse.getTaskId());
        completeTaskRequestBody.setFormDefinitionId(easyTaskInfoResponse.getTaskFormKeyId());
        completeTaskRequestBody.setOutcome(outCome);
        completeTaskRequestBody.setUserId(easyTaskInfoResponse.getUserId());
        if (StringUtils.isEmpty(completeTaskRequestBody.getUserId())) {
            completeTaskRequestBody.setUserId(getOneUserByRole(easyTaskInfoResponse.getRoleKey()));
        }
        completeTaskRequestBody.setVariables(variables);

        CompleteTaskResponseBody completeTaskResponseBodyResult = taskApi.completeTaskWithForm(completeTaskRequestBody);
        assertSuccess(completeTaskResponseBodyResult);
        printJsonString(completeTaskResponseBodyResult);

        return completeTaskResponseBodyResult;
    }


    /**
     * 通过流程实例Id 和 任务名称查询待办任务
     */
    private EasyTaskInfoResponse getEasyTaskInfo(String processInstanceId, String taskName, Long times) {

        if (System.currentTimeMillis() - times > 10000) {
            logger.error("获取不到待办任务：" + taskName);
            return null;
        }

        FindTaskRequestBody findTaskRequestBody = new FindTaskRequestBody();
        findTaskRequestBody.setProcessInsId(processInstanceId);
        findTaskRequestBody.setTaskName(taskName);

        EasyTaskInfoResponse taskEasyInfo = taskApi.getTask(findTaskRequestBody);

        if (taskEasyInfo == null) {
            try {
                logger.error("没有获取到待办任务，2秒后再次获取");
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getEasyTaskInfo(processInstanceId, taskName, times);
        }

        assertSuccess(taskEasyInfo);

        return taskEasyInfo;
    }

    /**
     * 通过角色ID查询其中的一个用户
     */
    private String getOneUserByRole(String roleKey) {
        // 默认返回系统用户
        return "rent-server000000000000000000000";
    }
}
