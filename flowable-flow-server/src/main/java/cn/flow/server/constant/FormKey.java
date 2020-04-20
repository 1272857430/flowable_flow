package cn.flow.server.constant;

import java.util.HashMap;
import java.util.Map;

public class FormKey {

    /** 备注 **/
    private static final String FORM_COMMON_MEMO = "form-common-memo";

    public static final Map<String,String> FORMS = new HashMap<>();
    static {
        FORMS.put(FORM_COMMON_MEMO, "处理");
    }
}
