package base;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Testbase {
    public ApplicationContext getContext(){
        String[] conf = {"spring.xml"};
        ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
        return ac;
    }
    @Test
    public void test1(){
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
    }
}
