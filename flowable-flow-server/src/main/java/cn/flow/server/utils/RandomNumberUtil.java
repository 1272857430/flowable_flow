package cn.flow.server.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取随机给定范围内N个不重复的数
 */
public class RandomNumberUtil {

    /**
     * @param args 随机数范围
     * @param num  获取随机数个数
     */
    public static List<Integer> random(int args,int num){
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < num;) {
            int randomNum = (int)(Math.random()*args);
            if (!result.contains(randomNum) && randomNum != args) {
                result.add(randomNum);
                i++;
            }
        }
        return result;
    }
}
