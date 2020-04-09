package cn.e3mall.publish;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @ClassName TestPublish
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/26 20:21
 * @Version 1.0
 **/
public class TestPublish {

    @Test
    public void testPublish() throws InterruptedException, IOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        /*while (true)
            Thread.sleep(10000);*/
        System.out.println("服务已经启动。。。");
        int read = System.in.read();
        System.out.println("服务已经关闭");


    }

}
