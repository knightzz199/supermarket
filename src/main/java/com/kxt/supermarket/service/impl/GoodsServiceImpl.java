package com.kxt.supermarket.service.impl;

import com.kxt.supermarket.pojo.Goods;
import com.kxt.supermarket.redis.GoodsKey;
import com.kxt.supermarket.service.BaseService;
import com.kxt.supermarket.service.GoodsService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Kxt
 * @Date 2021/8/1 22:31
 * @Version 1.0
 */

@Service
public class GoodsServiceImpl extends BaseService implements GoodsService {
    @Override
    public List<Goods> queryAllGoodsUpPrice() {
        List<Goods> list = redisService.getList(GoodsKey.getByUpPrice, null, Goods.class);
        if (list!=null){
            return list;
        }else {
            list = goodsMapper.queryAllGoodsUpPrice();
            if (list!=null){
                redisService.setList(GoodsKey.getByUpPrice, null, list);
                redisService.expire(GoodsKey.getByUpPrice, null, 60*60*24);
                return list;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<Goods> queryAllGoodsDownPrice() {
        List<Goods> list = redisService.getList(GoodsKey.getByDownPrice, null, Goods.class);
        if (list!=null){
            return list;
        }else {
            list = goodsMapper.queryAllGoodsDownPrice();
            if (list!=null){
                redisService.setList(GoodsKey.getByDownPrice, null, list);
                redisService.expire(GoodsKey.getByDownPrice, null, 60*60*24);
                return list;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<Goods> queryAllGoodsBySale() {
        List<Goods> list = redisService.getList(GoodsKey.getBySale, null, Goods.class);
        if (list!=null){
            return list;
        }else {
            list = goodsMapper.queryAllGoodsBySale();
            if (list!=null){
                redisService.setList(GoodsKey.getBySale, null, list);
                redisService.expire(GoodsKey.getBySale, null, 60*60*24);
                return list;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<Goods> queryAllGoodsByAdminId(Integer aid) {
        List<Goods> list = redisService.getList(GoodsKey.getByAdminId, aid+"", Goods.class);
        if (list!=null){
            return list;
        }else {
            list = goodsMapper.queryAllGoodsByAdminId(aid);
            if (list!=null){
                redisService.setList(GoodsKey.getByAdminId, aid+"", list);
                redisService.expire(GoodsKey.getByAdminId, aid+"", 60*60*24);
                return list;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<Goods> queryGoodsByState(Integer state) {
        List<Goods> list = redisService.getList(GoodsKey.getByState, null, Goods.class);
        if (list!=null){
            return list;
        }else {
            list = goodsMapper.queryGoodsByState(state);
            if (list!=null){
                redisService.setList(GoodsKey.getByState, state+"", list);
                redisService.expire(GoodsKey.getByState, state+"", 60*60*24);
                return list;
            }else {
                return null;
            }
        }
    }

    @Override
    public Goods queryGoodsById(Integer id) {
        Goods goods = redisService.get(GoodsKey.getById, id + "", Goods.class);
        if (goods!=null){
            return goods;
        }else {
            goods = goodsMapper.queryGoodsById(id);
            if (goods!=null){
                redisService.set(GoodsKey.getById, id+"", goods);
                redisService.expire(GoodsKey.getById, id+"", 60*60*24);
                return goods;
            }else {
                return null;
            }
        }
    }

    //controller再封装
    @Override
    public boolean addGoods(Goods goods) {
        return goodsMapper.addGoods(goods)==1;
    }

    @Override
    public boolean deleteGoodsById(Integer id) {
        Goods goods = queryGoodsById(id);
        Integer integer = goodsMapper.deleteGoodsById(id);
        if (integer==1){
            //删除和该商品有关的所有缓存
            redisService.del(GoodsKey.getById, id+"");
            redisService.del(GoodsKey.getByUpPrice,null);
            redisService.del(GoodsKey.getByDownPrice,null);
            redisService.del(GoodsKey.getBySale,null);
            redisService.del(GoodsKey.getByState,goods.getState()+"");
            redisService.del(GoodsKey.getByAdminId, goods.getAdminId()+"");
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateGoods(Goods goods) {
        Integer integer = goodsMapper.updateGoods(goods);
        if (integer==1){
            //删除和该商品有关的所有缓存
            redisService.del(GoodsKey.getById, goods.getId()+"");
            redisService.del(GoodsKey.getByDownPrice,null);
            redisService.del(GoodsKey.getBySale,null);
            redisService.del(GoodsKey.getByState,goods.getState()+"");
            redisService.del(GoodsKey.getByAdminId, goods.getAdminId()+"");
            redisService.del(GoodsKey.getByUpPrice,null);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Goods> searchFromEs(String content) throws IOException {
        SearchRequest request = new SearchRequest("supermarket");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder goodsDescription = QueryBuilders.matchQuery("goodsDescription", content);
        searchSourceBuilder.query(goodsDescription);

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("goodsDescription")
                        .preTags("<span style='color:red;font-weight:500'>")
                        .postTags("</span>")
                        .requireFieldMatch(false);
        searchSourceBuilder.highlighter(highlightBuilder);
        request.source(searchSourceBuilder);

        //执行搜索
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        //用一个集合保存结果集用于返回
        List<Goods> goodsList = new ArrayList<>();
        for (SearchHit hit : hits){
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取高亮的字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField field = highlightFields.get("goodsDescription");

            Goods tempGoods = new Goods();
            tempGoods.setId((Integer) sourceAsMap.get("id"));
            if (field!=null){
                tempGoods.setGoodsDescription(field.fragments()[0].toString());
            }else {
                tempGoods.setGoodsDescription((String) sourceAsMap.get("goodsDescription"));
            }
            tempGoods.setPicUrl((String) sourceAsMap.get("picUrl"));
            tempGoods.setStock((Integer) sourceAsMap.get("stock"));
            tempGoods.setState((Integer) sourceAsMap.get("state"));
            tempGoods.setSaleNum((Integer) sourceAsMap.get("saleNum"));
            tempGoods.setGoodsPrice((Double) sourceAsMap.get("goodsPrice"));
            tempGoods.setAdminId((Integer) sourceAsMap.get("adminId"));
            tempGoods.setTypeId((Integer) sourceAsMap.get("typeId"));

            goodsList.add(tempGoods);
        }
        return goodsList;
    }
}
