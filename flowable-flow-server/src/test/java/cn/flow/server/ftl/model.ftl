package cn.sayyoo.workflow.testCase;

import cn.sayyoo.workflow.RobotTestCaseBaseModel;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ${className} extends RobotTestCaseBaseModel {

    private static String PROCESS_KEY = "${processKey}";
    private static String PROCESS_NAME = "${processName}";
    private static String PROCESS_SCOPE_ID = "a9b58329e9d245279f36581248ce77a6";
    private static String OUT_COME = "aaa";

    <#list methods as method>
    private Map<String, Object> ${method.methodName}() {
        Map<String, Object> map = new HashMap<>();
        map.put("exampleKey", "exampleValue");
        return map;
    }

    </#list>
    <#list pathInfoVoList as pathInfo>
    /**
    *<#list pathInfo.pathPointVos as pathPoint> ${pathPoint.pointChineseName} >>>> </#list>
    */
    @Test
    public void allTest${pathInfo_index}() {
        // userId 只用作发起流程，之后的用户都是节点权限用户有关系
        String userId = "ea7b029803c94c739720e869643344cb";

    <#list pathInfo.methodVoList as method>
        <#if method_index == 0>
        String processInstanceId = startComponent(userId, PROCESS_KEY, PROCESS_NAME, PROCESS_SCOPE_ID, ${method.methodName}());
        <#else>
        keepOnWithForm(processInstanceId, "${method.taskName}", OUT_COME, ${method.methodName}());
        </#if>

    </#list>
    }

    </#list>
}
