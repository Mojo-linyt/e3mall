package cn.e3mall.jedis;

import cn.e3mall.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName TestJedisClient
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/2 23:56
 * @Version 1.0
 **/
public class TestJedisClient {

    @Test
    public void testJedisClientOnly(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("jedisClient", "单机版");
        String s = jedisClient.get("jedisClient");
        System.out.println(s);

    }

    @Test
    public void testJedisClientMany(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("jedisClient", "集群版");
        String s = jedisClient.get("jedisClient");
        System.out.println(s);

    }

}
