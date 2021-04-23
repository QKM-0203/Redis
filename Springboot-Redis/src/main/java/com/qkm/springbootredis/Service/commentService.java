package com.qkm.springbootredis.Service;

import com.qkm.springbootredis.Mapper.CommentMapper;
import com.qkm.springbootredis.POJO.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class commentService {

    private final CommentMapper commentMapper;
    private final RedisTemplate<String,Object> redisTemplate;

    public commentService(CommentMapper commentMapper, @Qualifier("myRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.commentMapper = commentMapper;
        this.redisTemplate = redisTemplate;
    }



    @Cacheable(cacheNames = "comment")
    public Comment getComment(String bossId){
        Comment comment = commentMapper.searchByBossid(bossId);
        redisTemplate.opsForValue().set("comment:#{bossId}",comment);
       return  comment;
    }

}
