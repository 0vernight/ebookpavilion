package com.mike.junit;

import org.junit.Test;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @Author: 23236
 * @createTime: 2022/11/25   15:41
 * @description:
 **/
public class RedisConnectTest {

    public static void main(String[] args) {

    }

    @Test
    public void  TestRedis() {

        System.out.println("开始");
        Jedis jedis = new Jedis("redis://default:mike@redis-14645.c251.east-us-mz.azure.cloud.redislabs.com:14645");
        Connection connection = jedis.getConnection();
        System.out.println(connection.ping());
        String select = connection.select(0);
        System.out.println(select);
        Set<String> keys = jedis.keys("*");
        System.out.println(keys.toString());
        List<String> name_pws = jedis.mget("name","psw");
        System.out.println(name_pws);

        System.out.println("喝西北风");

    }
}
