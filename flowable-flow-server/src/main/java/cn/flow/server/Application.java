package cn.flow.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan({"cn.flow.server", "cn.flow.api", "cn.flow.component"})
@EnableCaching //开启缓存
@EnableTransactionManagement // 开启事务管理
//@EntityScan(basePackages = {"com.flow.component", "cn.flow.server"})
@EnableJpaRepositories(basePackages = {"cn.flow.component", "cn.flow.server"})
@SpringBootApplication
public class Application
{
    public static void main( String[] args )
    {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.run(args);
    }
}
