package cn.flow.server.base;

public enum Mode {
    LOCAL("http://127.0.0.1:21601"),
    DEV("http://192.168.1.88:21601"),
    TEST("http://gwtest.sayyoo.cn/"),
    PRD("http://121.196.207.31:21601");

    Mode(String workflowUrl){
            this.workflowUrl = workflowUrl;
    }

    private String workflowUrl;

    public String getWorkflowUrl() {
        return workflowUrl;
    }

    public void setWorkflowUrl(String workflowUrl) {
        this.workflowUrl = workflowUrl;
    }

}
