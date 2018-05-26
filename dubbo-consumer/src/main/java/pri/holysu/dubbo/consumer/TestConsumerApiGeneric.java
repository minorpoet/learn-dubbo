package pri.holysu.dubbo.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pri.holysu.dubbo.api.Person;
import pri.holysu.dubbo.api.UserServiceBo;

import java.util.HashMap;
import java.util.Map;

/**
 * 泛化接口调用方式 不依赖服务提供方的契约类，可用来实现框架集成（如，通用服务测试框架）
 * https://dubbo.incubator.apache.org/books/dubbo-user-book/quick-start.html
 */
public class TestConsumerApiGeneric {

    public static void main(String[] args) throws JsonProcessingException {

        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-generic-consumer");

        // 注册中心testPojo
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("127.0.0.1:2181");
        registry.setProtocol("zookeeper");

        // 泛型参数设置为 GenericService, 具体的调用是设置为 接口类 UserServiceBo
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setVersion("1.0.0");
        reference.setGroup("dubbo");
        reference.setTimeout(3000);

        // 设置为泛化
        reference.setInterface(UserServiceBo.class);
        reference.setGeneric(true);


        GenericService userService = reference.get();

        Object result = userService.$invoke("sayHello",new String[] {String.class.getName()},new Object[]{"hah "});
        System.out.println(new ObjectMapper().writeValueAsString(result));

        // 将pojo转为 map
        // 泛化调用方式所有的参数均由 map 表示
        Map<String,Object> map = new HashMap<>();
        map.put("class","pri.holysu.dubbo.api.PersonImpl"); // 指定参数类型
        map.put("name","holysu");     // 指定参数属性值
        map.put("password","password");
        result = userService.$invoke("testPojo", new String[]{Person.class.getName()},new Object[]{map});
        System.out.println(result);
    }
}
