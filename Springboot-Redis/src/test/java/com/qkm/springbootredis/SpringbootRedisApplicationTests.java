package com.qkm.springbootredis;

import com.alibaba.fastjson.JSON;
import com.qkm.springbootredis.POJO.User;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;

@SpringBootTest
class SpringbootRedisApplicationTests {

    @Test
    void contextLoads() {
    }


    /**
     * RedisTemplate的五大类型操作都在opsFor底下,但是对于事务和crud就可以直接调用就行
     */
    @Autowired
    @Qualifier("myRedisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test(){
        //获取数据库连接的
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //清空所有的数据库数据
        connection.flushAll();
        connection.flushDb();

        redisTemplate.opsForValue().set("string","其开梦");
//        redisTemplate.opsForValue().set("string",new Long(1L));
        redisTemplate.delete("string");
        System.out.println(redisTemplate.opsForValue().get("string"));
    }


    /**
     *  //在企业中对象都是要序列化的,一般都是序列化成JSON,也可以是实现Serializable接口到达序列化
     *  为什么要序列化:
     * 1. 序列化最终的目的是为了对象可以跨平台存储，和进行网络传输。而我们进行跨平台存储和网络传输的方式就是IO，而我们的IO支持的数据格式就是字节数组。
     * 2.因为我们单方面的只把对象转成字节数组还不行，因为没有规则的字节数组我们是没办法把对象的本来面目还原回来的，
     * 所以我们必须在把对象转成字节数组的时候就制定一种规则（序列化），那么我们从IO流里面读出数据的时候再以这种规则把对象还原回来（反序列化）。
     * 3.如果我们要把一栋房子从一个地方运输到另一个地方去，序列化就是我把房子拆成一个个的砖块放到车子里，然后留下一张房子原来结构的图纸，
     * 反序列化就是我们把房子运输到了目的地以后，根据图纸把一块块砖头还原成房子原来面目的过程
     *
     *
     */
    @Test
    /**
     * 默认使用JDK序列化,但是要实现Serializable接口,所以如果自己定义redisTemplate就不用实现接口
     */
    public void testObject(){
        User qkm = new User("qkm", 18);
//
//        //使用JSON序列化输出,
//        String s = JSON.toJSONString(qkm);
//        //{"age":18,"name":"qkm"}
//        redisTemplate.opsForValue().set("user",s);
//        System.out.println(redisTemplate.opsForValue().get("user"));


        //在自定义的RedisTemplate中设置了序列化的格式(RedisConfig中)
        redisTemplate.opsForValue().set("user",qkm);
        System.out.println(redisTemplate.opsForValue().get("user"));//User(name=qkm, age=18)




        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("name","qkm");
        stringObjectHashMap.put("age","18");
        redisTemplate.opsForHash().putAll("hash",stringObjectHashMap);
        System.out.println(redisTemplate.opsForHash().values("hash"));//[qkm, 18]

    }
}
