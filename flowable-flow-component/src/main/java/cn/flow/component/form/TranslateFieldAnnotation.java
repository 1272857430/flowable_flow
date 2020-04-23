package cn.flow.component.form;

import cn.flow.component.exception.ComponentException;
import cn.flow.component.form.annotation.Form;
import cn.flow.component.form.annotation.FormField;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslateFieldAnnotation {

    public final static String PROCESS_SCOPE_ID = "processScopeId";
    public final static String OUT_COME = "outcome";

    /**
     * 获取formKey
     */
    public static String getFormKey(Class tClass){
        Form from = (Form) tClass.getAnnotation(Form.class);
        if (from == null || StringUtils.isEmpty(from.name())) {
            throw new ComponentException("没有定义Form或配置Form.name参数");
        }
        return from.name();
    }

    /**
     * 解析完整的from
     */
    public static Map<String, Object> translateFormData(Class tClass) {

        Map<String, Object> map = new HashMap<>();
        Form from = (Form) tClass.getAnnotation(Form.class);

        if (from == null || StringUtils.isEmpty(from.name())) {
            throw new ComponentException("没有定义Form或配置Form.name参数");
        }

        map.put("key", from.name());
        map.put("name", from.name());
        map.put("version", from.version());
        map.put("outcomes", new ArrayList());
        map.put("fields", translateFormField(tClass));

        return map;
    }

    /**
     * 解析field
     */
    private static List<Map<String, Object>> translateFormField(Class tClass) {

        List<Map<String, Object>> mapList = new ArrayList<>();
        Field[] fields = tClass.getDeclaredFields();

        for (Field field : fields) {
            FormField formField = field.getAnnotation(FormField.class);
            if (formField == null)
                continue;
            Map<String, Object> map = new HashMap<>();

            map.put("fieldType", formField.fieldType());
            map.put("id", field.getName());
            map.put("name", formField.name());
            map.put("type", formField.type());
            map.put("value", StringUtils.isEmpty(formField.value()) ? null : formField.value());
            map.put("required", formField.required());
            map.put("readOnly", formField.readOnly());
            map.put("overrideId", formField.overrideId());
            map.put("placeholder", StringUtils.isEmpty(formField.placeholder()) ? null : formField.placeholder());
            map.put("layout", StringUtils.isEmpty(formField.layout()) ? null : formField.layout());

            if (field.getType().isEnum()) {
                map.put("optionType", null);
                map.put("hasEmptyValue", formField.hasEmptyValue());
                JSONArray enumArray = new JSONArray();
                for (Object enumConstant : field.getType().getEnumConstants()) {
                    FormBaseEnum baseEnum = (FormBaseEnum) enumConstant;
                    JSONObject enumData = new JSONObject();
                    enumData.put("id", baseEnum.getCode());
                    enumData.put("name", baseEnum.getName());
                    enumArray.add(enumData);
                }
                map.put("options", enumArray);
            }
            mapList.add(map);
        }
        // 为流程添加默认ProcessScopeId
        mapList.add(defaultProcessScopeId(PROCESS_SCOPE_ID));
        mapList.add(defaultProcessScopeId(OUT_COME));

        return mapList;
    }

    // 为流程添加默认ProcessScopeId
    private static Map<String, Object> defaultProcessScopeId(String key) {
        Map<String, Object> map = new HashMap<>();
        map.put("fieldType", "FormField");
        map.put("id", key);
        map.put("name", key);
        map.put("type", "string");
        map.put("value", null);
        map.put("required", false);
        map.put("readOnly", false);
        map.put("overrideId", true);
        map.put("placeholder", null);
        map.put("layout", null);
        return map;
    }
}
