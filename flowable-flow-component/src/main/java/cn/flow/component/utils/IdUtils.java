package cn.flow.component.utils;

import java.util.UUID;

public class IdUtils {

    public static String nextId(){
        return "sid-"+ UUID.randomUUID().toString();
    }
}
