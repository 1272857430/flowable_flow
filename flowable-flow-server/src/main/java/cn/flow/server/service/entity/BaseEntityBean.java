package cn.flow.server.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.Objects;

@Data
@MappedSuperclass
public class BaseEntityBean {

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "idGenerator", strategy = "uuid")//这个是hibernate的注解/生成32位UUID
    @GeneratedValue(generator = "idGenerator")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @CreationTimestamp
    @Column(name = "CREATE_TIME", updatable = false)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @UpdateTimestamp
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @ColumnDefault("0")
    @Generated(GenerationTime.INSERT)
    @Column(name = "IS_DELETE", length = 2, nullable = false)
    private Integer isDelete;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntityBean that = (BaseEntityBean) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(isDelete, that.isDelete);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        return result;
    }


}
