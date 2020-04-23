package cn.flow.api.enums;


import org.springframework.util.StringUtils;

public enum ProcessStatus {

    FINISHED("FINISHED", "已结束"),
    UNFINISHED("UNFINISHED", "进行中");

    private String code;
    private String name;

    ProcessStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private String getNameByCode(String code){
        if (StringUtils.isEmpty(code))
            return null;
        for (ProcessStatus value : ProcessStatus.values()) {
            if (value.getCode().equals(code)){
                return value.name;
            }
        }
        return null;
    }
}
