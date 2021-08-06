package com.kxt.supermarket;

import com.kxt.supermarket.pojo.Admin;
import com.kxt.supermarket.redis.AdminKey;
import com.kxt.supermarket.redis.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

@SpringBootTest
class SupermarketApplicationTests {
    @Autowired
    private RedisService redisService;

    @Autowired
    private ElasticsearchRestTemplate template;
    @Test
    void test1(){
        boolean supermarket = template.indexOps(IndexCoordinates.of("supermarket")).exists();
        System.out.println(supermarket);
    }
    @Test
    void test2(){
        Admin admin = redisService.get(AdminKey.getByUsername, "asd", Admin.class);
        System.out.println(admin);
    }

}
