package com.kxt.supermarket.service.impl;

import com.kxt.supermarket.pojo.UserCollection;
import com.kxt.supermarket.redis.UserCollectionKey;
import com.kxt.supermarket.service.BaseService;
import com.kxt.supermarket.service.UserCollectionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/1 17:29
 * @Version 1.0
 */

@Service
public class UserCollectionServiceImpl extends BaseService implements UserCollectionService {
    @Override
    public UserCollection queryUserCollectionById(Integer id) {
        UserCollection userCollection = redisService.get(UserCollectionKey.getById, id + "", UserCollection.class);
        if (userCollection!=null){
            return userCollection;
        }else {
            userCollection = userCollectionMapper.queryUserCollectionById(id);
            if (userCollection!=null){
                redisService.set(UserCollectionKey.getById, id+"", userCollection);
                redisService.expire(UserCollectionKey.getById, id+"",60*60*24);
                return userCollection;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<UserCollection> queryUserCollectionByUid(Integer uid) {
        List<UserCollection> list = redisService.getList(UserCollectionKey.getByUid, uid + "", UserCollection.class);
        if (list!=null){
            return list;
        }else {
            list = userCollectionMapper.queryUserCollectionByUid(uid);
            if (list!=null){
                redisService.setList(UserCollectionKey.getByUid, uid+"", list);
                redisService.expire(UserCollectionKey.getByUid, uid+"",60*60*24);
                return list;
            }else {
                return null;
            }
        }
    }

    @Override
    public boolean addUserCollection(Integer uid, Integer gid) {
        return userCollectionMapper.addUserCollection(new UserCollection(null, uid, gid)) == 1;
    }

    @Override
    public boolean deleteUserCollectionById(Integer id) {
        UserCollection userCollection = queryUserCollectionById(id);
        Integer integer = userCollectionMapper.deleteUserCollectionById(id);
        if (integer==1){
            redisService.del(UserCollectionKey.getById, id+"");
            redisService.del(UserCollectionKey.getByUid,userCollection.getUserId()+"");
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateUserCollection(UserCollection userCollection) {
        Integer integer = userCollectionMapper.updateUserCollection(userCollection);
        if (integer==1){
            redisService.del(UserCollectionKey.getById, userCollection.getId()+"");
            redisService.del(UserCollectionKey.getByUid,userCollection.getUserId()+"");
            return true;
        }else {
            return false;
        }
    }
}
