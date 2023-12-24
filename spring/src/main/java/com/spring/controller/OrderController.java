package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.entity.Orders;
import com.spring.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
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
    public boolean Buy(@RequestBody Orders order) {
        System.out.println(order);
        ordersService.saveOrUpdate(order);
        return true;
    }

    @GetMapping("/findorderbystatus")
    public List<Orders> findOrderByStatus(@RequestParam Integer id, Integer status) {
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getOrderuserid, id);
        if (status != -1) {
            lambdaQueryWrapper.eq(Orders::getStatus, status);
        }
        List<Orders> dbOrder = ordersService.list(lambdaQueryWrapper);
        return dbOrder;
    }

    @GetMapping("/getallorder")
    public List<Orders> getAllOrder(){
        return ordersService.list();
    }

    @GetMapping("/findorderbyid")
    public Orders findOrderById(@RequestParam Integer id) {
//        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Orders::getId, id);
        return ordersService.getById(id);
    }
}
