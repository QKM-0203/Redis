#Spring boot 整合 Redis
**Springboot2以上的版本没有采用之前的Jedis来实现java操作redis,使用的是lettuce模式来实现java操作redis**

**原因:**
* jedis 是采用直连的模式,多个线程操作的话是不安全的,如果想要避免不安全,就得采用jedis pool连接池,更像是BIO(同步堵塞模式)
* lettuce 采用netty实现,实例可以在多个线程中进行共享,不存在线程不安全的情况,可以减少线程数据,更像是NIO模式(同步非堵塞模式)
## 导入依赖
```java
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
## 配置.yml/.properties文件
```java
spring:
  redis:
    host: 127.0.0.1
    port: 6379
```
## 测试
```java
    /**
     * RedisTemplate的五大类型操作都在opsFor底下,但是对于事务和crud就可以直接调用就行
     */
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    public void test(){
        //获取数据库连接的
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //清空所有的数据库数据
        connection.flushAll();
        connection.flushDb();

        redisTemplate.opsForValue().set("string","其开梦");
        redisTemplate.delete("string");
        System.out.println(redisTemplate.opsForValue().get("string"));
    }
```
## 自定义RedisTemplate
**redisTemplate默认采用的是JDK序列化,JDK序列化要实现接口,但是在终端中的key是乱码,使用自己设定的一般
都是JSON就不会是乱码**
```java
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
```
    