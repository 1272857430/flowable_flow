package cn.flow.server.service.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PROCESS_LOCK")
public class ProcessLockEntity extends BaseEntityBean {

    @Column(name = "PROCESS_KEY")
    private String processKey;

    @Column(name = "PROCESS_NAME")
    private String processName;

    @Column(name = "IS_LOCK")
    private String isLock;

    @Column(name = "LOCK_TIME")
    private int lockTime;
}
