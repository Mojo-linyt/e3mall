package cn.e3mall.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @ClassName TestInitSpring
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/13 15:47
 * @Version 1.0
 **/
public class TestInitSpring {

    @Test
    public void initSpring() throws IOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        System.in.read();

    }

    @Test
    public void testLong(){

        Long aLong = new Long(19);
        Long aLong3 = new Long("19");
        Long aLong1 = Long.valueOf(19);
        if (aLong1.equals(aLong3)){
            System.out.println("true");
        } else
            System.out.println("false");


    }

}
