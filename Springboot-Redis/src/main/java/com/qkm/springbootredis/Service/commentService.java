package com.qkm.springbootredis.Service;

import com.qkm.springbootredis.Mapper.CommentMapper;
import com.qkm.springbootredis.POJO.Comment;
import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
//该注解属于类级别的
//@CacheConfig(cacheNames = "comment")
public class commentService {

    private final CommentMapper commentMapper;
    private final RedisTemplate<String,Object> redisTemplate;

    public commentService(CommentMapper commentMapper, @Qualifier("myRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.commentMapper = commentMapper;
        this.redisTemplate = redisTemplate;
    }


    /**
     * @Cacheable  当该方法的返回值在缓存里面没有时,就会调用
     * @param bossId
     * @return
     */
    @Cacheable(cacheNames = "comment",key = "#bossId")
    public Comment getComment(String bossId){
        System.out.println("查询");
        Comment comment = commentMapper.searchByBossid(bossId);
        //redisTemplate.opsForValue().set("comment:#{bossId}",comment);
       return  comment;
    }



//
//                    //更新的注解,先执行下面的方法在更新缓存,如果方法抛出异常,则不更新缓存
//                    //result.id就是结果的返回值的id值
//    @CachePut(cacheNames="comment",key = "#result.bossid")
//    public Comment updateComment(String bossId){
//        System.out.println("更新");
//        commentMapper.updateCommentByBossid("测试redis缓存1",bossId);
//    }
//


    //更新的注解,先执行下面的方法在更新缓存,如果方法抛出异常,则不更新缓存
    //result.id就是结果的返回值的id值
    @CachePut(cacheNames="comment",key = "#result.bossid")
    public Comment updateComment(Comment comment){
        System.out.println("更新");
        commentMapper.updatebyBossId(comment);
       // int i = 9/0;
        return comment;
    }


}
