package cn.flow.component.taskShow.dao;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name ="PROCESS_DEFINITION_TASK_SHOW_CONFIG")
public class ProcessDefinitionTaskShowConfigEntity {

    @Id
    @Column(name = "ID_")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "PROCESS_KEY")
    private String processKey;

    @Column(name = "DEFINITION_TASK_ID")
    private String definitionTaskId;

    @Column(name = "DISPLAY_INDEX")
    private int displayIndex;

    @Column(name = "display")
    private Boolean display;

    public ProcessDefinitionTaskShowConfigEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
