package com.kxt.supermarket.redis;

/**
 * @Author Kxt
 * @Date 2021/7/27 23:21
 * @Version 1.0
 */
public class OrderKey extends BasePrefix{
    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getById = new OrderKey("id");
    //根据订单号查询
    public static OrderKey getByNum = new OrderKey("num");
    //根据用户id查询
    public static OrderKey getByUid = new OrderKey("uid");
    //根据状态查询 0未付款，1已付款，2表示未发货，3表示已发货，4表示已完成
    public static OrderKey getByState = new OrderKey("state");
}
