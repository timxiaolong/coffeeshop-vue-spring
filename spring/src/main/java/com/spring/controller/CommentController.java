package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.entity.Comment;
import com.spring.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wyj
 * @since 2023-12-12
 */
@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/getcommbyid")
    public List<Comment> GetCommById(@RequestParam("id") Integer id){
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getItemid,id);
        return commentService.list(lambdaQueryWrapper);
    }

    @GetMapping("/getallcomm")
    public List<Comment> getAllComm(){
        return commentService.list();
    }

}
