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


    @GetMapping("/update")
    public Comment updateComment(){
        return commentService.updateComment(new Comment("测试redis3", "2021-04-23 12:59:56", "2021-02-22 19:43:5612345", "123456"));
    }
}
