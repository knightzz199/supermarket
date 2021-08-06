package com.kxt.supermarket.controller;

import com.kxt.supermarket.Utils.ImageUtils;
import com.kxt.supermarket.Utils.JSONUtils;
import com.kxt.supermarket.Utils.Result;
import com.kxt.supermarket.pojo.Goods;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/3 15:56
 * @Version 1.0
 */

@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController{
    @GetMapping("/getAllGoodsUpPrice")
    public String getAllGoodsUpPrice(){
        List<Goods> goodsList = goodsService.queryAllGoodsUpPrice();
        return JSONUtils.objectToJson(Result.success(goodsList));
    }
    @GetMapping("/getAllGoodsDownPrice")
    public String queryAllGoodsDownPrice(){
        List<Goods> goodsList = goodsService.queryAllGoodsDownPrice();
        return JSONUtils.objectToJson(Result.success(goodsList));
    }
    @GetMapping("/getAllGoodsBySale")
    public String getAllGoodsBySale(){
        List<Goods> goodsList = goodsService.queryAllGoodsBySale();
        return JSONUtils.objectToJson(Result.success(goodsList));
    }
    @GetMapping("/getAllGoodsByAdminId")
    public String getAllGoodsByAdminId(Integer aid){
        List<Goods> goodsList = goodsService.queryAllGoodsByAdminId(aid);
        return JSONUtils.objectToJson(Result.success(goodsList));
    }
    @GetMapping("/getAllGoodsByState")
    public String getAllGoodsByState(Integer state){
        List<Goods> goodsList = goodsService.queryGoodsByState(state);
        return JSONUtils.objectToJson(Result.success(goodsList));
    }
    @GetMapping("/getGoodsById")
    public String getGoodsById(Integer id){
        Goods goods = goodsService.queryGoodsById(id);
        return JSONUtils.objectToJson(Result.success(goods));
    }

    //增加商品，需要上传商品图片
    @PostMapping("/addGoods")
    public String addGoods(String goodsDescription, Integer stock, Double goodsPrice,
                           Integer adminId, Integer typeId, MultipartFile file) throws IOException {

        String picUrl = ImageUtils.upload(file);
        Goods goods = new Goods();
        goods.setGoodsDescription(goodsDescription);
        goods.setStock(stock);
        goods.setGoodsPrice(goodsPrice);
        goods.setAdminId(adminId);
        goods.setTypeId(typeId);
        goods.setState(1);
        goods.setSaleNum(0);
        goods.setPicUrl(picUrl);
//        Goods goods = new Goods(null, goodsDescription, picUrl, stock, 1, 0, goodsPrice, adminId, typeId);
        boolean b = goodsService.addGoods(goods);
        if (b){
            rabbitTemplate.convertAndSend("es", "goods_save", goods.getId());
            return JSONUtils.objectToJson(Result.success(true));
        }else {
            return JSONUtils.objectToJson(Result.success(false));
        }
    }

    //删除商品
    @GetMapping("/deleteGoods")
    public String deleteGoods(Integer id){
        boolean b = goodsService.deleteGoodsById(id);
        if (b){
            rabbitTemplate.convertAndSend("es", "goods_delete", id);
            return JSONUtils.objectToJson(Result.success(true));
        }else {
            return JSONUtils.objectToJson(Result.success(false));
        }
    }
    @PostMapping("/updateGoods")
    public String updateGoods(Goods goods){
        boolean b = goodsService.updateGoods(goods);
        if (b){
            rabbitTemplate.convertAndSend("es", "goods_save", goods.getId());
            return JSONUtils.objectToJson(Result.success(true));
        }else {
            return JSONUtils.objectToJson(Result.success(false));
        }
    }

    //用ES搜索
    @GetMapping("/searchFromEs")
    public String searchFromEs(String content) throws IOException {
        List<Goods> goodsList = goodsService.searchFromEs(content);
        return JSONUtils.objectToJson(Result.success(goodsList));
    }
}
