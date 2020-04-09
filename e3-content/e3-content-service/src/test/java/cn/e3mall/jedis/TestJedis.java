package cn.e3mall.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName TestJedis
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/1 15:50
 * @Version 1.0
 **/
public class TestJedis {

    @Test
    public void testJedis(){

        //创建一个Jedis对象连接redis,参数：host,port
        Jedis jedis = new Jedis("192.168.25.128", 7004);
        jedis.set("test1", "my first jedis!");
        String test1 = jedis.get("test1");
        System.out.println(test1);
        jedis.close();

    }

    @Test
    public void testJedisPool(){

        JedisPool jedisPool = new JedisPool("192.168.25.128", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("test", "史蒂夫!");
        String test1 = jedis.get("test");
        System.out.println(test1);
        jedis.close();
        jedisPool.close();

    }

    @Test
    public void testJedisCluster() throws IOException {

        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.128",7001));
        nodes.add(new HostAndPort("192.168.25.128",7002));
        nodes.add(new HostAndPort("192.168.25.128",7003));
        nodes.add(new HostAndPort("192.168.25.128",7004));
        nodes.add(new HostAndPort("192.168.25.128",7005));
        nodes.add(new HostAndPort("192.168.25.128",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("testCluster", "jedisCluster");
        String s = jedisCluster.get("testCluster");
        System.out.println(s);
        jedisCluster.close();

    }

}
