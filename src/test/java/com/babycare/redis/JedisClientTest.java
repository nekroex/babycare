package com.babycare.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by huiyuanwhy on 17/6/25.
 */
public class JedisClientTest {


    @Test
    public void test1(){
        Jedis jedis = new Jedis("10.244.4.117",6379);
        jedis.set("mom","shuyuan");
        String value = jedis.get("mom");
        System.out.println(value);

    }



}
