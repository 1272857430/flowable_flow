package cn.flow.server;

import cn.flow.component.form.config.CustomFormEngine;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableCaching //开启缓存
@EnableTransactionManagement // 开启事务管理
@ComponentScan(basePackages = {"cn.flow.api", "cn.flow.component", "cn.flow.server"})
@EntityScan(basePackages = {"cn.flow.api", "cn.flow.component", "cn.flow.server"})
@EnableJpaRepositories(basePackages = {"cn.flow.api", "cn.flow.component", "cn.flow.server"})
@SpringBootApplication
public class Application {
    public static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main( String[] args ) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.run(args);
    }

    @Bean
    public CommandLineRunner init(final CustomFormEngine customFormEngine, final @Autowired @Qualifier("processEngine") ProcessEngine processEngine) {
        ProcessEngineConfigurationImpl configuration = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
        configuration.getFormEngines().put(CustomFormEngine.FORM_ENGINE_NAME,customFormEngine);
        logger.info("add "+CustomFormEngine.FORM_ENGINE_NAME+" engine .");
        return strings -> {};
    }
}
