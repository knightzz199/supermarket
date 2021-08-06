package com.kxt.supermarket.service.impl;

import com.kxt.supermarket.pojo.Type;
import com.kxt.supermarket.redis.TypeKey;
import com.kxt.supermarket.service.BaseService;
import com.kxt.supermarket.service.TypeService;
import org.springframework.stereotype.Service;

/**
 * @Author Kxt
 * @Date 2021/8/1 10:47
 * @Version 1.0
 */

@Service
public class TypeServiceImpl extends BaseService implements TypeService {
    @Override
    public boolean deleteTypeById(Integer id) {
        Type type = queryTypeById(id);
        Integer integer = typeMapper.deleteTypeById(id);
        if (integer==1){
            redisService.del(TypeKey.getById, id+"");
            redisService.del(TypeKey.getByName,type.getName());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean addType(String name) {
        Type type = queryTypeByName(name);
        if (type!=null){
            return false;
        }else {
            return typeMapper.addType(new Type(null, name)) == 1;
        }
    }

    @Override
    public boolean updateType(Type type) {
        Type type1 = queryTypeById(type.getId());
        if (type.getName().equals(type1.getName())){
            return false;
        }else {
            Integer integer = typeMapper.updateType(type);
            if (integer==1){
                redisService.del(TypeKey.getById, type.getId()+"");
                redisService.del(TypeKey.getByName, type1.getName());
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public Type queryTypeById(Integer id) {
        Type type = redisService.get(TypeKey.getById, id + "", Type.class);
        if (type!=null){
            return type;
        }else {
            type = typeMapper.queryTypeById(id);
            if (type!=null){
                redisService.set(TypeKey.getById, id+"", type);
                redisService.expire(TypeKey.getById, id+"", 60*60*48);
                return type;
            }else {
                return null;
            }
        }
    }

    @Override
    public Type queryTypeByName(String name) {
        Type type = redisService.get(TypeKey.getByName, name, Type.class);
        if (type!=null){
            return type;
        }else {
            type = typeMapper.queryTypeByName(name);
            if (type!=null){
                redisService.set(TypeKey.getByName, name, type);
                redisService.expire(TypeKey.getByName, name, 60*60*48);
                return type;
            }else {
                return null;
            }
        }
    }
}
