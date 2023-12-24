package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.entity.Shoppingcarft;
import com.spring.service.ShoppingcarftService;
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
@RequestMapping("/shoppingcarft")
public class ShoppingcarftController {
    @Autowired
    private ShoppingcarftService service;

    @GetMapping("/getcarft")
    public List<Shoppingcarft> getCarft(@RequestParam Integer userid){
        LambdaQueryWrapper<Shoppingcarft> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shoppingcarft::getUserid,userid);
        return service.list(lambdaQueryWrapper);
    }
}
