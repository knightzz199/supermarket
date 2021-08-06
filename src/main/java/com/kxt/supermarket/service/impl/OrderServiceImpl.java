package com.kxt.supermarket.service.impl;

import com.kxt.supermarket.pojo.Order;
import com.kxt.supermarket.redis.OrderKey;
import com.kxt.supermarket.service.BaseService;
import com.kxt.supermarket.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/2 21:57
 * @Version 1.0
 */

@Service
public class OrderServiceImpl extends BaseService implements OrderService{

    @Override
    public Order queryOrderById(Integer id) {
        Order order = redisService.get(OrderKey.getById, id + "", Order.class);
        if (order!=null){
            return null;
        }else {
            order = orderMapper.queryOrderById(id);

            if (order!=null){
                redisService.set(OrderKey.getById, id+"", order);
                redisService.expire(OrderKey.getById, id+"", 60*60*24);
                return order;
            }else {
                return null;
            }
        }
    }

    @Override
    public Order queryOrderByNum(String oderNum) {
        Order order = redisService.get(OrderKey.getByNum, oderNum, Order.class);
        if (order!=null){
            return order;
        }else {
            order = orderMapper.queryOrderByNum(oderNum);
            if (order!=null){
                redisService.set(OrderKey.getByNum, oderNum, order);
                redisService.expire(OrderKey.getByNum, oderNum, 60*60*24);
                return order;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<Order> queryAllOrderByUid(Integer uid) {
        List<Order> list = redisService.getList(OrderKey.getByUid, uid + "", Order.class);
        if (list!=null){
            return list;
        }else {
            list = orderMapper.queryAllOrderByUid(uid);
            if (list!=null){
                redisService.setList(OrderKey.getByUid, uid+"", list);
                redisService.expire(OrderKey.getByUid, uid+"", 60*60*24);
                return list;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<Order> queryOrderByState(Integer state) {
        List<Order> list = redisService.getList(OrderKey.getByState, state + "", Order.class);
        if (list!=null){
            return list;
        }else {
            list = orderMapper.queryOrderByState(state);
            if (list!=null){
                redisService.setList(OrderKey.getByState, state+"", list);
                redisService.expire(OrderKey.getByState, state+"", 60*60*24);
                return list;
            }else {
                return null;
            }
        }
    }

    @Override
    public boolean addOrder(Order order) {
        //controller再封装
        return orderMapper.addOrder(order) ==1;
    }

    @Override
    public boolean deleteOrderById(Integer id) {
        Order order = queryOrderById(id);
        Integer integer = orderMapper.deleteOrderById(id);
        if (integer == 1){
            redisService.del(OrderKey.getById, id + "");
            redisService.del(OrderKey.getByNum, order.getOrderNum() + "");
            redisService.del(OrderKey.getByUid, order.getUserId() + "");
            redisService.del(OrderKey.getByState, order.getState() + "");
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateOrder(Order order) {
        Integer integer = orderMapper.updateOrder(order);
        if (integer == 1){
            redisService.del(OrderKey.getById, order.getId() + "");
            redisService.del(OrderKey.getByNum, order.getOrderNum() + "");
            redisService.del(OrderKey.getByUid, order.getUserId() + "");
            redisService.del(OrderKey.getByState, order.getState() + "");
            return true;
        }else {
            return false;
        }
    }
}
