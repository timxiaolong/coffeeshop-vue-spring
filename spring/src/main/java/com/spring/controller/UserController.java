package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.entity.Admin;
import com.spring.entity.Message;
import com.spring.entity.User;
import com.spring.service.UserService;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Message Login(@RequestParam("username") String name, @RequestParam("password") String password){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,name);
        List<User> dbUser= userService.list(lambdaQueryWrapper);
        System.out.println(dbUser);
        if (dbUser == null){// 如果没找到
            return new Message("账号或者密码不正确",400,null);
        }else if (!dbUser.get(0).getPassword().equals(password)){//如果密码不对
            return new Message("账号或密码不正确",400,null);
        }else {
            return new Message("登陆成功",200,dbUser.get(0));
        }
    }

    @PostMapping("/signin")
    public boolean Signin(@RequestBody User user){
        userService.save(user);
        return true;
    }

    @PostMapping("/changepwd")
    public Message ChangePwd(@RequestParam Integer id,String oldPwd, String confirmPwd){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,id);
        User user = userService.getById(id);
        user.setPassword(confirmPwd);
        if (!user.getPassword().equals(oldPwd)){
            return new Message("原密码输入不正确",500,null);
        }
        if (userService.update(user,lambdaQueryWrapper)){
            return new Message("修改成功！",200,true);
        }else {
            return new Message("修改失败！",500,false);
        }

    }

}
