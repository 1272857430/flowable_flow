package cn.flow.component.component;

import org.flowable.bpmn.model.Gateway;

public abstract class AbstractGatewayComponent extends AbstractComponent {

    private Gateway gateway;

    private String gatewayAdapterSpringBeanName;


    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public String getGatewayAdapterSpringBeanName() {
        return gatewayAdapterSpringBeanName;
    }

    public void setGatewayAdapterSpringBeanName(String gatewayAdapterSpringBeanName) {
        this.gatewayAdapterSpringBeanName = gatewayAdapterSpringBeanName;
    }

    @Override
    public void buildExtensionElements() {

    }
}
