package com.kxt.supermarket.service;

import com.kxt.supermarket.pojo.Order;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/2 21:50
 * @Version 1.0
 */

public interface OrderService {
    Order queryOrderById(Integer id);
    //根据订单号查询订单
    Order queryOrderByNum(String oderNum);
    //根据用户id列出其所有订单
    List<Order> queryAllOrderByUid(Integer uid);
    //根据订单状态查询订单 0未付款，1已付款，2表示未发货，3表示已发货，4表示已完成
    List<Order> queryOrderByState(Integer state);
    //6个参数需要自定义
    boolean addOrder(Order order);
    boolean deleteOrderById(Integer id);
    boolean updateOrder(Order order);
}
