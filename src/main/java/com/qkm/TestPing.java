package com.qkm;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class TestPing {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println(jedis.ping());//测试连接PONG
         Map<String,String> map =  new HashMap<>();
         map.put("name","qkm");
         map.put("age","18");
        jedis.hset("hashmap",map);
        System.out.println(jedis.hgetAll("hashmap"));//{age=18, name=qkm}
    }
}
