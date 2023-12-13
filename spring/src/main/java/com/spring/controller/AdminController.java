package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.entity.Admin;
import com.spring.service.AdminService;
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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public List<Admin> Login(@RequestBody Admin admin){
//        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.like(Admin::getUsername,admin.getUsername());
//        System.out.println(lambdaQueryWrapper);
//        List<Admin> dbAdmin= adminService.list(lambdaQueryWrapper);
//        return dbAdmin == null;
        return adminService.list();
    }
}
