package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.entity.Shoppingcarft;
import com.spring.service.ShoppingcarftService;
import io.swagger.models.auth.In;
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

    @PostMapping("/addToCarft")
    public boolean addToCarft(@RequestBody Shoppingcarft sc){
        System.out.println(sc);
        return service.save(sc);
    }

    @DeleteMapping("/deleteById")
    public boolean deleteById(@RequestParam Integer id){
        return service.removeById(id);
    }

    @DeleteMapping("/clean")
    public boolean clean(@RequestParam Integer userId){
        LambdaQueryWrapper<Shoppingcarft> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Shoppingcarft::getUserid,userId);
        return service.remove(lambdaQueryWrapper);
    }
}
