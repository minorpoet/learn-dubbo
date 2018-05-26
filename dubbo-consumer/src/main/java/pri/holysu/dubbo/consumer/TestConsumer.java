package pri.holysu.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import pri.holysu.dubbo.api.PersonImpl;
import pri.holysu.dubbo.api.UserServiceBo;

import java.io.IOException;

public class TestConsumer {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:consumer.xml");

        final UserServiceBo userService = (UserServiceBo) context.getBean("userService");

        System.out.println(userService.sayHello("宇宙无敌大魔王"));

        PersonImpl person = new PersonImpl();
        person.setName("holysu");
        person.setPassword("123");
        System.out.println(userService.testPojo(person));
        System.in.read();
    }
}
