package cn.flow.component.enums;

public enum ComponentType {

    /**
     * 简单启动节点
     */
    STARTEVENT_SIMPLE,

    /**
     * 网关启动节点
     */
    STARTEVENT_EXCLUSIVE,

    /**
     * 自动执行任务
     */
    SERVICE_TASK,

    /**
     * 用户任务
     */
    USERTASK_SIMPLE,

    /**
     * 排他网关
     */
    EXCLUSIVE_GATEWAY,

    /**
     * 并行网关
     */
    GATEWAY_PARALLEL,

    /**
     * 包容网关
     */
    GATEWAY_INCLUSIVE,

    /**
     * 用户任务 + 并行网关
     */
    USERTASK_GATEWAY,

    /**
     *  用户任务 + 排他网关
     */
    USERTASK_EXCLUSIVE,

    /**
     * 等待节点
     */
    RECEIVE_TASK,

    /**
     * 结束
     */
    END_EVENT
}
