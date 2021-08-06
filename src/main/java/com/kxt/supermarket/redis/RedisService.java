package com.kxt.supermarket.redis;

import com.kxt.supermarket.Utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author Kxt
 * @Date 2021/7/27 22:19
 * @Version 1.0
 */

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取某个对象
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        //真是的key
        String realKey = ObjectUtils.isEmpty(key) ? prefix.getPrefix() : prefix.getPrefix() +":"+ key;
        String str = redisTemplate.opsForValue().get(realKey);
        return JSONUtils.jsonToBean(str, clazz);
    }

    /**
     * 获取整个集合
     */
    public <T> List<T> getList(KeyPrefix prefix, String key, Class<T> clazz){
        String realKey = ObjectUtils.isEmpty(key) ? prefix.getPrefix() : prefix.getPrefix() +":"+ key;
        List<String> ranges = redisTemplate.opsForList().range(realKey, 0, -1);
        List<T> list=new ArrayList<>();

        if (ranges==null||ranges.size()==0){
            return null;
        }else {
            ranges.forEach(range->{
                T t = JSONUtils.jsonToBean(range, clazz);
                list.add(t);
            });
        }
        return list;
    }

    /**
     * 设计定时
     */
    public Boolean expire(KeyPrefix prefix,String key,int exTime){
        String realKey = ObjectUtils.isEmpty(key) ? prefix.getPrefix() : prefix.getPrefix() +":"+ key;
        return redisTemplate.expire(realKey, exTime, TimeUnit.SECONDS);
    }

    /**
     * 设置对象
     */
    public <T> void set(KeyPrefix prefix,String key,T value){
        String realKey = ObjectUtils.isEmpty(key) ? prefix.getPrefix() : prefix.getPrefix() +":"+ key;
        String str = JSONUtils.objectToJson(value);
        redisTemplate.opsForValue().set(realKey, str);
    }

    /**
     * 设置集合
     */
    public <T> Long setList(KeyPrefix prefix,String key,List<T> value){
        String realKey = ObjectUtils.isEmpty(key) ? prefix.getPrefix() : prefix.getPrefix() +":"+ key;
        List<String> list=new ArrayList<>();
        for (T t : value) {
            String str = JSONUtils.objectToJson(t);
            list.add(str);
        }
        //返回为设置集合的数量
        return redisTemplate.opsForList().leftPushAll(realKey, list);
    }

    /**
     * 删除对象
     */
    public Boolean del(KeyPrefix prefix,String key){
        String realKey = ObjectUtils.isEmpty(key) ? prefix.getPrefix() : prefix.getPrefix() +":"+ key;
        return redisTemplate.delete(realKey);
    }

    /**
     * 删除多个对象
     */
    public int delAll(KeyPrefix prefix){
        Set<String> keys = keys(prefix);
        for (String key : keys){
            redisTemplate.delete(key);
        }
        return keys.size();
    }

    /**
     * 判断key是否存在
     */
    public Boolean isExists(KeyPrefix prefix,String key){
        String realKey = ObjectUtils.isEmpty(key) ? prefix.getPrefix() : prefix.getPrefix() +":"+ key;
        return redisTemplate.hasKey(realKey);
    }

    /**
     * 根据大键查询所有键
     */
    public Set<String> keys(KeyPrefix prefix){
        String str = prefix+"*";
        return redisTemplate.keys(str);
    }

}
