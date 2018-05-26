package pri.holysu.dubbo.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pri.holysu.dubbo.api.Person;
import pri.holysu.dubbo.api.UserServiceBo;

import java.util.concurrent.TimeUnit;

public class UserServiceImpl implements UserServiceBo {

    @Override
    public String sayHello(String name) {
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        return "hello: " + name;
     }

    @Override
    public String testPojo(Person person) {
        try {
            return  new ObjectMapper().writeValueAsString(person);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
