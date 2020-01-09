package cn.flow.component.form.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFormEngineConfigurator {

    @Bean
    public CustomFormEngine createCustomFormEngine(){
        return new CustomFormEngine();
    }
}
