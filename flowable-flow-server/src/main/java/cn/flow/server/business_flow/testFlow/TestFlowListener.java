package cn.flow.server.business_flow.testFlow;

import cn.flow.component.listener.CustomTaskListener;
import lombok.extern.slf4j.Slf4j;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class TestFlowListener {

    @Bean("testListener")
    public CustomTaskListener testListener() {
        return new CustomTaskListener() {
            @Override
            public void create(DelegateTask delegateTask) {
                log.info("测试人工节点 create");
            }
            @Override
            public void complete(DelegateTask delegateTask) {
                log.info("测试人工节点 complete");
            }
            @Override
            public void delete(DelegateTask delegateTask) {
                log.info("测试人工节点 delete");
            }
            @Override
            public void assignment(DelegateTask delegateTask) {
                log.info("测试人工节点 assignment");
            }
        };
    }

}
