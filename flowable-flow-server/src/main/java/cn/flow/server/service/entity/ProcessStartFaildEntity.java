package cn.flow.server.service.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "PROCESS_START_FAIL")
public class ProcessStartFaildEntity extends BaseEntityBean {

    @Column(name = "PROCESS_KEY")
    private String processKey;

    @Column(name = "PROCESS_NAME")
    private String processName;

    @Column(name = "START_USER_ID")
    private String startUserId;

    @Column(name = "fail_reason")
    private String failReason;

    @Column(name = "start_param")
    private String startParam;

    @Column(name = "start_time")
    private Date startTime;
}
