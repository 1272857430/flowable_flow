package cn.flow.server.base;

public enum Mode {
    LOCAL("http://127.0.0.1:21601","http://192.168.1.33:10200"),
    DEV("http://192.168.1.88:21601","http://192.168.1.11:21000"),
    TEST("http://gwtest.sayyoo.cn/","http://gwtest.sayyoo.cn/"),
    PRD("http://121.196.207.31:21601","http://120.55.42.62:21000");

    Mode(String workflowUrl, String userApiUrl){
            this.workflowUrl = workflowUrl;
            this.userApiUrl = userApiUrl;
    }

    private String workflowUrl;
    private String userApiUrl;

    public String getWorkflowUrl() {
        return workflowUrl;
    }

    public void setWorkflowUrl(String workflowUrl) {
        this.workflowUrl = workflowUrl;
    }

    public String getUserApiUrl() {
        return userApiUrl;
    }

    public void setUserApiUrl(String userApiUrl) {
        this.userApiUrl = userApiUrl;
    }
}
