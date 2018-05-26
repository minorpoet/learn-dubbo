package pri.holysu.dubbo.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.RpcContext;
import javafx.scene.control.TableView;
import pri.holysu.dubbo.api.UserServiceBo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/** 异步调用 */
public class TestConsumerAsync {

    public static void main(String[] args) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-consumer-async");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("127.0.0.1:2181");
        registry.setProtocol("zookeeper");

        ReferenceConfig<UserServiceBo> reference = new ReferenceConfig<>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(UserServiceBo.class);
        reference.setVersion("1.0.0");
        reference.setGroup("dubbo");
        reference.setTimeout(3000);

        // 设为异步
        reference.setAsync(true);

        UserServiceBo service = reference.get();

        long startTime = System.currentTimeMillis() /1000;

        // 由于异步调用，返回null
        System.out.println(service.sayHello("scott"));

        Future<String> serviceFuture = RpcContext.getContext().getFuture();

        System.out.println(service.sayHello("nick "));

        Future<String> serviceFuture2 = RpcContext.getContext().getFuture();

        // sayHello 内部sleep 2秒，如果是顺序调用则要4秒；这边只需要2s
        try {
            System.out.println(serviceFuture.get());

            System.out.println(serviceFuture2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis()/1000;
        System.out.println("costs: " + (endTime-startTime));
    }
}
