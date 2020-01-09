package cn.flow.api.request.process;

import lombok.Data;

import java.util.List;

@Data
public class TypeNode {
    public static final String TYPE_GROUP = "1";
    public static final String TYPE_NODE = "2";

    private String id;
    private String text;
    private String name;
    private String parentId;
    private String type; // 1: group 2: type
    private String roleKey;

    private List<TypeNode> children;
}
