package cn.flow.server.testCase;

import cn.flow.server.RobotTestCaseBaseModel;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestFlowTemplateTestCase extends RobotTestCaseBaseModel {

    private static String PROCESS_KEY = "process-test-flow";
    private static String PROCESS_NAME = "测试流程";
    private static String PROCESS_SCOPE_ID = "2c2bbb3502fc416c85901d0c33589b83";
    private static String START_USER_ID = "0ddb03e677384ae8a8837cb4d457da48";
    private static String OUT_COME = "aaa";

    private Map<String, Object> start_2_1() {
        Map<String, Object> map = new HashMap<>();
        map.put("memo", "测试流程start1");
        return map;
    }

    private Map<String, Object> simpleComponent() {
        Map<String, Object> map = new HashMap<>();
        map.put("memo", "simpleComponent1");
        return map;
    }

    @Test
    public void allTest0() {
        String processInstanceId = startComponent(START_USER_ID, PROCESS_KEY, PROCESS_NAME, PROCESS_SCOPE_ID, start_2_1());
//        keepOnWithForm(processInstanceId, "测试人工节点", OUT_COME, simpleComponent());
    }
}
