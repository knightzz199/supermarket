package com.kxt.supermarket.mapper;

import com.kxt.supermarket.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/7/26 23:09
 * @Version 1.0
 */

@Mapper
public interface GoodsMapper {
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

    Integer addGoods(Goods goods);
    Integer deleteGoodsById(Integer id);
    Integer updateGoods(Goods goods);
}
