package cn.flow.api.response.form;

import lombok.Data;

import java.util.List;

@Data
public class Group {

    private String groupName;

    private List<String> items;
}
