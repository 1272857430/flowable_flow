package cn.flow.component.form.deploy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ACT_FO_FORM_RESOURCE")
public class FormResourceEntity {

    @Id
    @Column(name = "ID_")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @Column(name = "NAME_")
    protected String name;

    @Column(name = "RESOURCE_BYTES_")
    protected byte[] bytes;

    @Column(name = "DEPLOYMENT_ID_")
    protected String deploymentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }
}
