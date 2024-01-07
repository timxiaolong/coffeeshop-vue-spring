package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @GetMapping("/getmenubytype")
    public List<Menu> getMenuByType(@RequestParam Integer type){
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getTypeid,type);
        return menuService.list(wrapper);
    }

    @GetMapping("/getMenuByKeyword")
    public List<Menu> getMenuByKeyword(@RequestParam String Keyword){
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Menu::getName,Keyword);
        return menuService.list(wrapper);
    }

    @PostMapping("changeGoods")
    public boolean changeGoods(@RequestBody Menu menu){
        System.out.println(menu);
        return menuService.saveOrUpdate(menu);
    }
}
