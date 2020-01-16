package cn.flow.server.service.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PROCESS_START_CHANCE")
public class ProcessStartChanceEntity extends BaseEntityBean {

    @Column(name = "process_key")
    private String processKey;

    @Column(name = "process_name")
    private String processName;

    @Column(name = "process_scope_id")
    private String processScopeId;

    @Column(name = "process_scope_name")
    private String processScopeName;

    @Column(name = "start_chance")
    private Double startChance;
}
