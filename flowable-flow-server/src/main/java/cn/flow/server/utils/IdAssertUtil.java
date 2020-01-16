package cn.flow.server.utils;

import org.apache.commons.lang.StringUtils;

public class IdAssertUtil {

    public static boolean assertIdWidthIs32(String id){

        if(!StringUtils.isBlank(id) && id.length() == 32){
            return true;
        }
        return false;
    }
}
