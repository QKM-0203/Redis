package com.qkm.springbootredis.Mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qkm.springbootredis.POJO.Comment;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
* @Entity com.qkm.springbootredis.POJO.Comment
*/
@Repository
public interface CommentMapper extends BaseMapper<Comment> {


     Comment searchByBossid(@Param("bossid") String bossid);

     int updateCommentByBossid(@Param("comment") String comment, @Param("bossid") String bossid);

     int updatebyBossId(Comment comment);
}
