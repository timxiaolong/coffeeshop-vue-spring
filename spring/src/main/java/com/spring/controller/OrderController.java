package com.spring.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.entity.Orders;
import com.spring.service.OrdersService;
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
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/sendorder")
    public boolean Buy(@RequestBody Orders order){
        System.out.println(order);
        ordersService.saveOrUpdate(order);
        return true;
    }

}
