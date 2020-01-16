package cn.flow.server.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    /**
     * 根据键值对填充字符串，如("hello ${name}",{name:"xiaoming"})
     */
    public static String renderString(String content, Map<String, Object> map){
        Set<Map.Entry<String, Object>> sets = map.entrySet();
        for(Map.Entry<String, Object> entry : sets) {
            String value = String.valueOf(entry.getValue());
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(value);
        }
        return content;
    }

  public static void main(String[] args) {
      String content = "hello ${name}, 1 2 3 4 5 ${six} 7, again ${name1} again ${name2} again ${name3}. ";
      Map<String, Object> map = new HashMap<>();
      map.put("name", "java");
      map.put("six", 6.1f);
      map.put("name1", 6);
      map.put("name2", new Date().getTime());
      map.put("name3", null);
      content = StringHelper.renderString(content, map);
      System.out.println(content);
  }
}
