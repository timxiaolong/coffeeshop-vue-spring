package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.entity.*;
import com.spring.service.AdminService;
import com.spring.service.OrdersService;
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

    @Autowired
    private OrdersService ordersService;


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

    @GetMapping("/getOrderByStatus")
    public List<Orders> getOrderByStatus(@RequestParam Integer status){
        if (status == 0){
            return ordersService.list();
        }else {
            LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Orders::getStatus,status);
            return ordersService.list(lambdaQueryWrapper);
        }
}
    @PostMapping("/changePwd")
    public Message changePwd(@RequestParam Integer id, String newPwd, String oldPwd, String confirmPwd){
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getId,id);
        Admin dbAdmin = adminService.getOne(lambdaQueryWrapper);
        if (dbAdmin == null){
            return new Message("未找到用户信息",400,null);
        }else if (!dbAdmin.getPassword().equals(oldPwd)){
            return new Message("原密码输入错误",500,null);
        }else if (!confirmPwd.equals(newPwd)){
            return new Message("两次输入的密码不同",500,null);
        }else {
            dbAdmin.setUsername(newPwd);
            if (adminService.update(dbAdmin,lambdaQueryWrapper)){
                return new Message("修改成功！",200,dbAdmin);
            }else {
                return new Message("修改失败",500,null);
            }
        }
    }
}
