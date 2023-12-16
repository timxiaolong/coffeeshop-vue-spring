package com.spring.controller;


import com.spring.entity.Menu;
import com.spring.service.MenuService;
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
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/getmenu")
    public List<Menu> List(){
        return menuService.list();
    }

    @GetMapping("/getmenubyid")
    public Menu GetById(@RequestParam("id") String id){
        System.out.println(id);
        return menuService.getById(id);
    }
}
