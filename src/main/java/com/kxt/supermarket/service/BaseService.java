package com.kxt.supermarket.service;

import com.kxt.supermarket.elasticsearch.GoodsRepository;
import com.kxt.supermarket.mapper.*;
import com.kxt.supermarket.redis.RedisService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author Kxt
 * @Date 2021/7/31 0:22
 * @Version 1.0
 */


public class BaseService {
    @Resource
    protected AdminMapper adminMapper;

    @Resource
    protected AddressMapper addressMapper;

    @Resource
    protected GoodsMapper goodsMapper;

    @Resource
    protected OrderMapper orderMapper;

    @Resource
    protected TypeMapper typeMapper;

    @Resource
    protected UserCollectionMapper userCollectionMapper;

    @Resource
    protected UserMapper userMapper;

    @Autowired
    protected RedisService redisService;

    @Qualifier("elasticsearchClient")
    @Autowired
    protected RestHighLevelClient client;

    @Autowired
    protected GoodsRepository goodsRepository;
}
