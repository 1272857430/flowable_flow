package cn.flow.server.constant;

import java.util.HashMap;
import java.util.Map;

public class RoleKey {

    /** 测试角色 **/
    public static final String TEST_ROLE = "TEST_ROLE";

    // TODO 此处需演化为从权限处获取
    public static final Map<String,String> ROLES = new HashMap<>();
    static {
        ROLES.put(TEST_ROLE, "测试角色");
    }

}