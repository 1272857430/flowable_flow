package cn.flow.component.form.deploy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FormResourceEntityDao extends JpaRepository<FormResourceEntity, String>, JpaSpecificationExecutor<FormResourceEntity> {

    FormResourceEntity findByDeploymentIdAndName(String deploymentId, String name);
}
