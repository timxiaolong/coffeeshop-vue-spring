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
    public Message Login(@RequestParam("email") String email, @RequestParam("password") String password){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail,email);
        try {
            User dbUser = userService.getOne(lambdaQueryWrapper);
            if (!dbUser.getPassword().equals(password) || dbUser == null){//如果密码不对
                return new Message("账号或密码不正确",400,null);
            }else {
                return new Message("登陆成功",200,dbUser);
            }
        }catch (Exception e){
                return new Message("账号或者密码不正确",400,null);
        }


    }

    @PostMapping("/signin")
    public Message Signin(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getEmail,user.getEmail());
        User dbUser = userService.getOne(lambdaQueryWrapper);
        if (dbUser == null){
            if (userService.save(user)){
                return new Message("注册成功!",200,user);
            }else {
                return new Message("注册失败，请联系管理员",500,null);
            }
        }else {
            return new Message("当前邮箱已被注册，请直接登陆或更换其他邮箱",500,null);
        }
    }

    @PostMapping("/changepwd")
    public Message ChangePwd(@RequestParam Integer id,String oldPwd, String confirmPwd){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,id);
        User user = userService.getById(id);
        if (!user.getPassword().equals(oldPwd)){
            return new Message("原密码输入不正确",500,null);
        }
        user.setPassword(confirmPwd);
        if (userService.update(user,lambdaQueryWrapper)){
            return new Message("修改成功！",200,true);
        }else {
            return new Message("修改失败！",500,false);
        }
    }

    @PostMapping("/changeinfo")
    public Message changeInfo(@RequestBody User user){
        User dbUser = userService.getById(user.getId());
        dbUser.setName(user.getName());
        dbUser.setPhone(user.getPhone());
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,user.getId());
        if (userService.update(dbUser,lambdaQueryWrapper)){
            return new Message("修改成功！",200,true);
        }else {
            return new Message("修改失败",500,false);
        }
    }

    @GetMapping("/getuserinfobyid")
    public User getUserInfoById(@RequestParam Integer id){
        return userService.getById(id);
    }

    @GetMapping("/getalluser")
    public List<User> getAllUser(){
        return userService.list();
    }

    @DeleteMapping("/deleuserbyid")
    public boolean deleUserById(@RequestParam Integer id){
        return userService.removeById(id);
    }

    @GetMapping("/getUserByName")
    public List<User> getUserByName(@RequestParam String username){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,username);
        return userService.list(lambdaQueryWrapper);
    }
}
