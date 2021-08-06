package com.kxt.supermarket.rabbitmq;

import com.kxt.supermarket.pojo.Goods;
import com.kxt.supermarket.pojo.Order;
import com.kxt.supermarket.redis.RedisService;
import com.kxt.supermarket.redis.SecKillKey;
import com.kxt.supermarket.service.GoodsService;
import com.kxt.supermarket.service.OrderService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @Author Kxt
 * @Date 2021/8/5 0:11
 * @Version 1.0
 */

@Component
public class SecKillConsumer {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "supermarket",type = "topic"),
                    key = {"secKill"}
            )
    })
    public Boolean secKill(Number[] args){
        Integer num = (Integer) args[0];
        Double expressFee = (Double) args[1];
        Integer goodsId = (Integer) args[2];
        Integer userId = (Integer) args[3];
        Integer addressId = (Integer) args[4];
        Integer adminId = (Integer) args[5];
        Goods goods = goodsService.queryGoodsById(goodsId);
        Integer integer = redisService.get(SecKillKey.getByUid, userId + "", Integer.class);
        if (integer!=null){
            return false;
        }
        //二次判断商品库存,防止超卖
        if (goods.getStock()>0){
            goods.setStock(goods.getStock()-num);
            goods.setSaleNum(goods.getSaleNum()+num);
            boolean b = goodsService.updateGoods(goods);
            if (b){
                rabbitTemplate.convertAndSend("es", "goods_save", goods.getId());
                Order order = new Order();
                order.setUserId(userId);
                Double goodsPrice = goods.getGoodsPrice();
                order.setId(null);
                order.setNum(num);
                order.setExpressFee(expressFee);
                order.setPrice(goodsPrice*num);
                order.setGoodsId(goodsId);
                order.setAddressId(addressId);
                order.setAdminId(adminId);
                order.setState(0);
                order.setCreateTime(new Date());
                order.setTotalPrice(goodsPrice*num+expressFee);
                order.setOrderNum(UUID.randomUUID().toString());
                return orderService.addOrder(order);
            }
        }
        return false;
    }
}
