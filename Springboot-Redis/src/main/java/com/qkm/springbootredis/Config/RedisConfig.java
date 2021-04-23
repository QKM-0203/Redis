package com.qkm.springbootredis.Config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@EnableCaching
@Configuration
@MapperScan({"com/qkm/springbootredis/Mapper"})
public class RedisConfig {

    /**
     * redis的默认方式是JdkSerializationRedisSerializer
     * JdkSerializationRedisSerializer: 使用JDK提供的序列化功能。 优点是反序列化时不需要提供类型信息(class)，
     * 但缺点是需要实现Serializable接口，还有序列化后的结果非常庞大，是JSON格式的5倍左右，这样就会消耗redis服务器的大量内存。
     * Jackson2JsonRedisSerializer： 使用Jackson库将对象序列化为JSON字符串。优点是速度快，序列化后的字符串短小精悍，
     * 不需要实现Serializable接口。但缺点也非常致命，那就是此类的构造函数中有一个类型参数，必须提供要序列化对象的类型信息(.class对象)。
     * 通过查看源代码，发现其只在反序列化过程中用到了类型信息。
     *
     * 问题：使用默认的JDK序列化方式，在RDM工具中查看k-v值时会出现“乱码”，不方便查看。
     * 解决：自定义系列化方式，使用Jackson2JsonRedisSerializer
     *
     *
     **/
    @Bean
    public RedisTemplate<String, Object> myRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //使用JSON格式序列化对象，对缓存数据key和value进行转换
        Jackson2JsonRedisSerializer<Object> jackson = new Jackson2JsonRedisSerializer<>(Object.class);

        // 解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson.setObjectMapper(om);

        //字符串序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
          //将key序列化为String类型
        template.setKeySerializer(stringRedisSerializer);
          //hash的key也采用String类型
        template.setHashKeySerializer(stringRedisSerializer);
          //Value序列化为JSON类型
        template.setValueSerializer(jackson);
         //Hash的value序列化为JSON
        template.setHashValueSerializer(jackson);

        template.afterPropertiesSet();
        return template;
    }
}
