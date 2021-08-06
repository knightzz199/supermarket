package com.kxt.supermarket.mapper;

import com.kxt.supermarket.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/7/27 16:05
 * @Version 1.0
 */

@Mapper
public interface OrderMapper {
    Order queryOrderById(Integer id);
    //根据订单号查询订单
    Order queryOrderByNum(String oderNum);
    //根据用户id列出其所有订单
    List<Order> queryAllOrderByUid(Integer uid);
    //根据订单状态查询订单 0未付款，1已付款，2表示未发货，3表示已发货，4表示已完成
    List<Order> queryOrderByState(Integer state);

    Integer addOrder(Order order);
    Integer deleteOrderById(Integer id);
    Integer updateOrder(Order order);
}
