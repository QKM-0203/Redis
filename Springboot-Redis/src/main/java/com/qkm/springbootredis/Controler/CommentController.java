package com.qkm.springbootredis.Controler;


import com.qkm.springbootredis.POJO.Comment;
import com.qkm.springbootredis.Service.commentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {


    private final commentService commentService;

    public CommentController(commentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comment/{bossId}")
    public Comment getComment(@PathVariable("bossId") String bossId){
         return commentService.getComment(bossId);
    }
}
