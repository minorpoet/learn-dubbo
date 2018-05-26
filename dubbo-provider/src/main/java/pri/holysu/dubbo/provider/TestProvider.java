package pri.holysu.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TestProvider {

    public static void main(String[] args) throws IOException {
        System.setProperty("java.net.preferIPv4Stack","true");

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:provider.xml");

        context.start();

        // press any key to exit
        System.in.read();
    }
}
