package com.kxt.supermarket.redis;

/**
 * @Author Kxt
 * @Date 2021/7/27 23:09
 * @Version 1.0
 */

public class GoodsKey extends BasePrefix{
    private GoodsKey(String prefix) {
        super(prefix);
    }
    public static GoodsKey getById = new GoodsKey("id");
    //价格升序
    public static GoodsKey getByUpPrice = new GoodsKey("upPrice");
    //价格降序
    public static GoodsKey getByDownPrice = new GoodsKey("downPrice");
    //按销量排序
    public static GoodsKey getBySale = new GoodsKey("sale");
    //根据商家id查找所有商家的商品
    public static GoodsKey getByAdminId = new GoodsKey("aid");
    //根据状态查询商品
    public static GoodsKey getByState = new GoodsKey("state");
}
