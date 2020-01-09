package cn.flow.api.response.process;

import lombok.Data;

@Data
public class GroupModel {
    private String groupId;
    private String groupName;
    private String alias;

    public GroupModel() {
    }

    public GroupModel(String groupName, String alias) {
        this.groupName = groupName;
        this.alias = alias;
    }

    public GroupModel(String groupId, String groupName, String alias) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.alias = alias;
    }
}
