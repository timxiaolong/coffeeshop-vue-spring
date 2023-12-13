package com.spring.controller;


import com.spring.entity.Admin;
import com.spring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wyj
 * @since 2023-12-12
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Admin Login(@RequestBody Admin admin){
        return admin;
    }
}
