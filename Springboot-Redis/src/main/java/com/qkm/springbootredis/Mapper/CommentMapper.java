package com.qkm.springbootredis.Mapper;
import java.util.List;
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

}
