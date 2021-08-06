package com.kxt.supermarket.rabbitmq;

import com.kxt.supermarket.elasticsearch.GoodsRepository;
import com.kxt.supermarket.pojo.Goods;
import com.kxt.supermarket.service.GoodsService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Kxt
 * @Date 2021/8/3 22:43
 * @Version 1.0
 */

@Component
public class GoodsConsumer {

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    GoodsService goodsService;

    @RabbitListener(bindings = {
            @QueueBinding(
                value = @Queue,
                    exchange = @Exchange(value = "es",type = "topic"),
                    key = {"goods_save"}
            )
    })
    public void saveGoods(Integer id){
        Goods goods = goodsService.queryGoodsById(id);
        //更新时，根据id判断相同来判断是插入新数据还是修改
        goodsRepository.save(goods);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "es",type = "topic"),
                    key = {"goods_delete"}
            )
    })
    public void deleteGoods(Integer id){
        goodsRepository.deleteById(id);
    }
}
