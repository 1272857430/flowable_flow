package cn.flow.server.base;

import cn.flow.component.utils.JsonFormater;
import com.alibaba.fastjson.JSON;
import feign.Feign;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;

import java.lang.reflect.Method;
import java.util.Collection;

@SuppressWarnings({"WeakerAccess", "unused"})
@Slf4j
public class BaseTestCase extends Assert {

    public <T> T todo(Class<T> clazz, String serverUrl){
        SpringMvcContract contract = new SpringMvcContract();
        Feign.Builder builder = Feign.builder()
                .contract(contract)
                .decoder(new JacksonDecoder())
                .encoder(new FeignSpringFormEncoder());
        return builder.target(clazz, serverUrl);
    }

    public <T> T todo(Class<T> clazz, String serverUrl, RequestInterceptor requestInterceptor){
        SpringMvcContract contract = new SpringMvcContract();
        Feign.Builder builder = Feign.builder()
                .contract(contract)
                .decoder(new JacksonDecoder())
                .encoder(new FeignSpringFormEncoder())
                .requestInterceptor(requestInterceptor);
        return builder.target(clazz, serverUrl);
    }

    public void printJsonString(Object o){
        System.out.println(JsonFormater.format(JSON.toJSONString(o)));
    }

    protected void assertAllFields(Object object1, Object object2) {

        if (object1 == null || object2 == null) {
            String errMsg = "null object found: ";
            errMsg += "\nObject1: " + object1;
            errMsg += "\nObject2: " + object2;
            log.error(errMsg);
            assert (false);
        }

        Method[] methods = object1.getClass().getMethods();
        try {
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    Object val1 = method.invoke(object1);
                    Object val2 = method.invoke(object2);
                    assertValue(method.getName(), val1, val2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void assertValue(String methodName, Object val1, Object val2) {
        if (val1 instanceof Collection) {
            // 如果是集合成员，先判断大小是否相同
            Collection col1 = (Collection) val1;
            Collection col2 = (Collection) val1;
            if (col1.size() != col2.size()) {
                String errMsg = "Failed to verify method result: " + methodName;
                errMsg += "\nValue of object1: " + val1;
                errMsg += "\nValue of object2: " + val2;
                log.error(errMsg);
                assert (false);
            }

            Object[] arr1 = col1.toArray();
            Object[] arr2 = col2.toArray();
            for (int i = 0; i < arr1.length; i++) {
                // 递归调用比较下一层成员
                assertValue(methodName, arr1[i], arr2[i]);
            }
        } else {
            if (val1 != null && !val1.equals(val2)) {
                String errMsg = "Failed to verify method result: " + methodName;
                errMsg += "\nValue of object1: " + val1;
                errMsg += "\nValue of object2: " + val2;
                log.error(errMsg);
                assert (false);
            }
        }
    }

    protected String newUUID() {
        return this.newUUID(32);
    }

    protected String newUUID(int len) {
        StringBuilder bld = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int type = (int)(Math.random() * 3);
            switch (type) {
                case 0:
                    bld.append((char)(Math.random() * 26 + 'a'));
                    break;
                case 1:
                    bld.append((char)(Math.random() * 26 + 'A'));
                    break;
                case 2:
                    bld.append((char)(Math.random() * 10 + '0'));
                    break;
                default:
                    bld.append((char)(Math.random() * 26 + 'a'));
            }

        }
        return bld.toString();
    }

    protected void assertEqual(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) {
            return;
        }

        if (obj1 == null || obj2 == null) {
            log.error("Failed to assert equal objects: \nObject1: " + obj1 + "\nObject2: " + obj2);
            assert(false);
        }

        if (!obj1.equals(obj2)) {
            log.error("Failed to assert equal objects: \nObject1: " + obj1 + "\nObject2: " + obj2);
            assert(false);
        }
    }

    protected void assertNotEqual(Object obj1, Object obj2) {
        if (obj1 == null || obj2 == null) {
            log.error("Failed to assert non-equal objects: \nObject1: " + obj1 + "\nObject2: " + obj2);
            assert(false);
        }

        if (obj1.equals(obj2)) {
            log.error("Failed to assert non-equal objects: \nObject: " + obj1);
            assert(false);
        }
    }

    protected void assertSuccess(Object obj) {
        try {
            Method method = obj.getClass().getMethod("getCode");
            Object res = method.invoke(obj);
            if (!res.equals(200)) {
                String errMsg = "Failed to assert success code, returned code: " + res.toString();
                try {
                    method = obj.getClass().getMethod("getMessage");
                    res = method.invoke(obj);
                    errMsg += "\n" + res.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                log.error(errMsg);
                assert(false);
            }
        } catch (Exception e) {
            log.error("Failed to assert success code, object: " + obj.toString());
            assert(false);
        }
        printJsonString(obj);
    }
}
