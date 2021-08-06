package com.kxt.supermarket.controller;

import com.kxt.supermarket.elasticsearch.GoodsRepository;
import com.kxt.supermarket.redis.RedisService;
import com.kxt.supermarket.service.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @Author Kxt
 * @Date 2021/8/2 22:30
 * @Version 1.0
 */

public class BaseController{
    @Autowired
    protected AddressService addressService;
    @Autowired
    protected AdminService adminService;
    @Autowired
    protected GoodsService goodsService;
    @Autowired
    protected OrderService orderService;
    @Autowired
    protected TypeService typeService;
    @Autowired
    protected UserCollectionService userCollectionService;
    @Autowired
    UserService userService;


    @Autowired
    RedisService redisService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    GoodsRepository goodsRepository;
    @Qualifier("elasticsearchClient")
    @Autowired
    RestHighLevelClient client;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    public void serReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = request.getSession(true);
    }

}
