package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.entity.Admin;
import com.spring.entity.Menu;
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
    public Admin Login(@RequestParam("username") String username,@RequestParam("password") String password){
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Admin::getUsername,username);
        List<Admin> dbAdmin= adminService.list(lambdaQueryWrapper);
        System.out.println(dbAdmin);
        if (dbAdmin == null){
            return null;
        }else {
            return dbAdmin.get(0);
        }
    }



}
