package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.entity.Admin;
import com.spring.entity.Menu;
import com.spring.entity.Message;
import com.spring.entity.User;
import com.spring.service.AdminService;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    @GetMapping("/login")
    public Message Login(@RequestParam("username") String username, @RequestParam("password") String password){
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getUsername,username);
        Admin dbAdmin = adminService.getOne(lambdaQueryWrapper);
        if (dbAdmin == null){
            return new Message("没有找到用户",400,null);
        }else if (!dbAdmin.getPassword().equals(password)){
            return new Message("用户名或密码不正确",500,null);
        }else {
            return new Message("登陆成功",200,dbAdmin);
        }
    }



}
