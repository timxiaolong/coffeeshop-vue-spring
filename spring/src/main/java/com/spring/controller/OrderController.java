package com.spring.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.entity.Orders;
import com.spring.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    //商品下单、付款
    @PostMapping("/sendorder")
    public boolean Buy(@RequestBody Orders order) {
        order.setOrdertime(LocalDateTime.now());
        order.setPretime(LocalDateTime.now().plusMinutes(40L));
        order.setStatus(1);
        System.out.println("order");
        return ordersService.saveOrUpdate(order);
    }

    //购物车批量下单
    @PostMapping("/sendOrders")
    public boolean buyByCarft(@RequestBody List<Orders> list){
        int flag = 0;
        System.out.println(list);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setOrdertime(LocalDateTime.now());
            list.get(i).setPretime(LocalDateTime.now().plusMinutes(40L));
            list.get(i).setStatus(0);
            if (!ordersService.save(list.get(i))){
                flag = flag+1;
            }
        }
        return (flag == 0);
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

    @PutMapping("/changeorder")
    public boolean changeOrders(@RequestBody Orders orders){
        System.out.println(orders);
        Orders dbOrders = ordersService.getById(orders.getId());
        dbOrders.setNum(orders.getNum());
        dbOrders.setStatus(orders.getStatus());
        System.out.println(dbOrders);
        return ordersService.updateById(dbOrders);
    }

    @DeleteMapping("/deleOrderById")
    public boolean deleOrderById(@RequestParam Integer id){
        return ordersService.removeById(id);
    }

    @PutMapping("/changeOrderStatus")
    public boolean changeOrderStatus(@RequestParam Integer id,Integer status){
        Orders dborder = ordersService.getById(id);
        dborder.setStatus(status);
        System.out.println(dborder);
        return ordersService.updateById(dborder);
    }

    @PutMapping("/confirmComm")
    public boolean confirmComm(@RequestParam Integer id){
        Orders dbOrder = ordersService.getById(id);
        if (dbOrder != null){
            dbOrder.setHascomm("Y");
        }
        return ordersService.updateById(dbOrder);
    }
}
