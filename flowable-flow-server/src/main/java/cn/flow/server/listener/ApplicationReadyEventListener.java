package cn.flow.server.listener;

import cn.flow.component.form.config.CustomFormEngine;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * application ready event, some like CommandLineRunner, after CommandLineRunner execute
 */
//@Slf4j
//@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

//    private final ProcessEngine processEngine;
//
//    private final CustomFormEngine customFormEngine;
//
//    @Autowired
//    public ApplicationReadyEventListener(@Qualifier("processEngine") ProcessEngine processEngine, CustomFormEngine customFormEngine) {
//        this.processEngine = processEngine;
//        this.customFormEngine = customFormEngine;
//    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        ProcessEngineConfigurationImpl configuration = (ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration();
//        configuration.getFormEngines().put(CustomFormEngine.FORM_ENGINE_NAME,customFormEngine);
//        log.info("add "+CustomFormEngine.FORM_ENGINE_NAME+" engine .");
//        log.info("application ready event ...");
//        configuration.getEventDispatcher().addEventListener(new MyEventListener());
    }


}