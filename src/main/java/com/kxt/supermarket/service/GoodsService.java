package com.kxt.supermarket.service;

import com.kxt.supermarket.pojo.Goods;

import java.io.IOException;
import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/1 22:18
 * @Version 1.0
 */
public interface GoodsService {
    //商品按价格升序排列
    List<Goods> queryAllGoodsUpPrice();
    //商品按价格降序排列
    List<Goods> queryAllGoodsDownPrice();
    //商品按销量降序排列
    List<Goods> queryAllGoodsBySale();
    //根据商家id查询所有商家的商品
    List<Goods> queryAllGoodsByAdminId(Integer aid);
    //根据状态查询商品 0:下架 1:可以购买 2:卖完了
    List<Goods>queryGoodsByState(Integer state);

    Goods queryGoodsById(Integer id);

    boolean addGoods(Goods goods);
    boolean deleteGoodsById(Integer id);
    boolean updateGoods(Goods goods);

    //从ES中搜索商品并高亮关键词
    List<Goods> searchFromEs(String content) throws IOException;
}
