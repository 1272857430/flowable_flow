package cn.flow.component.taskShow.dao;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Table(name = "PROCESS_INSTANCE_TASK_SHOW_CONFIG", indexes = {@Index(name = "PITSC_INS_ID", columnList = "PROCESS_INSTANCE_ID", unique = true)})
public class ProcessInstanceTaskShowConfigEntity {

    @Id
    @Column(name = "ID_")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "PROCESS_INSTANCE_ID")
    private String processInstanceId;

    @Column(name = "PROCESS_KEY")
    private String processKey;

    @Column(name = "DEFINITION_TASK_ID")
    private String definitionTaskId;

    @Column(name = "INSTANCE_TASK_ID")
    private String instanceTaskId;

    @Column(name = "DISPLAY_INDEX")
    private int displayIndex;

    @Column(name = "DISPLAY")
    private Boolean display;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getDefinitionTaskId() {
        return definitionTaskId;
    }

    public void setDefinitionTaskId(String definitionTaskId) {
        this.definitionTaskId = definitionTaskId;
    }

    public String getInstanceTaskId() {
        return instanceTaskId;
    }

    public void setInstanceTaskId(String instanceTaskId) {
        this.instanceTaskId = instanceTaskId;
    }

    public int getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(int displayIndex) {
        this.displayIndex = displayIndex;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }
}
