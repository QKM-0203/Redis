package com.qkm;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTx {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","qkm");
        jsonObject.put("age","18");

        String s = jsonObject.toJSONString();
        jedis.flushAll();
        //开启事务
        Transaction multi = jedis.multi();
        try{
            multi.set("user1",s);
            multi.set("user2",s);
            //手动抛出异常
            int i = 4/0;
            //执行事务
            multi.exec();
        }catch(Exception e){
            //事务失效
            multi.discard();
            e.printStackTrace();
        }finally {
            System.out.println(jedis.get("user1"));//{"name":"qkm","age":"18"}
            System.out.println(jedis.get("user2"));// {"name":"qkm","age":"18"}
            //关闭连接
            jedis.close();
        }
    }
}
