package com.kxt.supermarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author Kxt
 * @Date 2021/7/26 19:55
 * @Version 1.0
 *
 * 配置使得RedisTemplate设置的key value 不会被序列化，能存入redis客户端数据库中
 */

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        return template;
    }

}
