package com.kxt.supermarket.controller;

import com.kxt.supermarket.Utils.JSONUtils;
import com.kxt.supermarket.Utils.Result;
import com.kxt.supermarket.pojo.Goods;
import com.kxt.supermarket.pojo.Order;
import com.kxt.supermarket.redis.SecKillKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author Kxt
 * @Date 2021/8/4 15:24
 * @Version 1.0
 */

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @GetMapping("/getOrderById")
    public String getOrderById(Integer id) {
        Order order = orderService.queryOrderById(id);
        return JSONUtils.objectToJson(Result.success(order));
    }

    //通过订单号查询订单
    @GetMapping("/getOrderByNum")
    public String getOrderByNum(String orderNum) {
        Order order = orderService.queryOrderByNum(orderNum);
        return JSONUtils.objectToJson(Result.success(order));
    }
    //通过订单状态查询对应订单 0未付款，1已付款，2表示未发货，3表示已发货，4表示已完成
    @GetMapping("/getOrderByState")
    public String getOrderByState(Integer state){
        List<Order> orders = orderService.queryOrderByState(state);
        return JSONUtils.objectToJson(Result.success(orders));
    }

    //用用户id查询其所有订单，相当于查询购物车
    @GetMapping("/getShoppingCart")
    public String getShoppingCart(Integer uid){
        List<Order> orders = orderService.queryAllOrderByUid(uid);
        return JSONUtils.objectToJson(Result.success(orders));
    }

    @PostMapping("/addOrder")
    public String addOrder(Integer num,Double expressFee,Integer goodsId,Integer userId,Integer addressId,Integer adminId){
        Order order = new Order();
        Goods goods = goodsService.queryGoodsById(goodsId);
        if (goods.getStock()<=0){
            return JSONUtils.objectToJson(Result.success(false));
        }
        if (goods.getStock()<num){
            return JSONUtils.objectToJson(Result.success(false));
        }
        Double goodsPrice = goods.getGoodsPrice();
        order.setId(null);
        order.setNum(num);
        order.setExpressFee(expressFee);
        order.setPrice(goodsPrice*num);
        order.setGoodsId(goodsId);
        order.setUserId(userId);
        order.setAddressId(addressId);
        order.setAdminId(adminId);
        order.setState(0);
        order.setCreateTime(new Date());
        order.setTotalPrice(goodsPrice*num+expressFee);
        order.setOrderNum(UUID.randomUUID().toString());
        boolean b = orderService.addOrder(order);
        if (b){
            goods.setStock(goods.getStock()-num);
            goods.setSaleNum(goods.getSaleNum()+num);
            boolean b1 = goodsService.updateGoods(goods);
            if (b1){
                rabbitTemplate.convertAndSend("es", "goods_save", goods.getId());
                return JSONUtils.objectToJson(Result.success(true));
            }else {
                return JSONUtils.objectToJson(Result.success(false));
            }
        }
        return JSONUtils.objectToJson(Result.success(false));
    }


    @GetMapping("/deleteOrderById")
    public String deleteOrderById(Integer id){
        boolean b = orderService.deleteOrderById(id);
        return JSONUtils.objectToJson(Result.success(b));
    }

    @PostMapping("/updateOrder")
    public String updateOrder(Order order){
        boolean b = orderService.updateOrder(order);
        return JSONUtils.objectToJson(Result.success(b));
    }

    //商品抢购(秒杀,用redis和rabbitmq实现)
    //商品抢购规则 : 每人只能购买一次商品,且不超过购买10个商品
    @PostMapping("/secKill")
    public String secKill(Integer num,Double expressFee,Integer goodsId,Integer userId,Integer addressId,Integer adminId){
        Integer integer = redisService.get(SecKillKey.getByUid, userId + "", Integer.class);
        if (integer!=null){
            return JSONUtils.objectToJson(Result.success(false));
        }
        if (num>10){
            return JSONUtils.objectToJson(Result.success(false));
        }
        Goods goods = goodsService.queryGoodsById(goodsId);
        if (goods.getStock()<=0){
            return JSONUtils.objectToJson(Result.success(false));
        }
        if (goods.getStock()<num){
            return JSONUtils.objectToJson(Result.success(false));
        }
        Boolean b = (Boolean) rabbitTemplate.convertSendAndReceive("supermarket", "secKill"
                , new Number[]{num, expressFee,goodsId,userId,addressId,adminId});
        if (b){
            redisService.set(SecKillKey.getByUid, userId+"", 1);
            redisService.expire(SecKillKey.getByUid, userId+"", 60*60*24);
            return JSONUtils.objectToJson(Result.success(true));
        }else {
            return JSONUtils.objectToJson(Result.success(false));
        }
    }
}
