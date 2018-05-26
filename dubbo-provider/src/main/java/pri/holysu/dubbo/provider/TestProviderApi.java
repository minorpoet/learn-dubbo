package pri.holysu.dubbo.provider;

import com.alibaba.dubbo.config.*;
import pri.holysu.dubbo.api.UserServiceBo;

import java.io.IOException;

/**
 * dubbo api 的方式搭建服务提供方
 */
public class TestProviderApi {

    public static void main(String[] args) throws IOException {
        UserServiceBo userServiceBo = new UserServiceImpl();

        // 相当于 <dubbo:application name="dubbo-provider-api"" />
        ApplicationConfig application  = new ApplicationConfig();
        application.setName("dubbo-provider-api");

        // 相当于 <dubbo:registry address="zookeeper://127.0.0.1:2181" />
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("127.0.0.1:2181");
        registry.setProtocol("zookeeper");

        // 协议
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20880);

        // 监控
        MonitorConfig monitorConfig = new MonitorConfig();
        monitorConfig.setProtocol("registry");

        // 发布服务, 等价于     <dubbo:service interface="pri.holysu.dubbo.api.UserServiceBo" ref="userService" group="dubbo" version="1.0.0" timeout="3000"/>
        ServiceConfig<UserServiceBo> service = new ServiceConfig<>();
        service.setApplication(application);
        service.setMonitor(monitorConfig);
        service.setProtocol(protocol);
        service.setRegistry(registry);
        service.setInterface(UserServiceBo.class);
        service.setRef(userServiceBo);
        service.setVersion("1.0.0.1");
        service.setGroup("dubbo");
        service.setTimeout(3000);
        service.export();

        System.in.read();
    }
}
