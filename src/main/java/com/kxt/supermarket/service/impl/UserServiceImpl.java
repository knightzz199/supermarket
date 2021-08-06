package com.kxt.supermarket.service.impl;

import com.kxt.supermarket.Utils.ImageUtils;
import com.kxt.supermarket.pojo.User;
import com.kxt.supermarket.redis.UserKey;
import com.kxt.supermarket.service.BaseService;
import com.kxt.supermarket.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author Kxt
 * @Date 2021/7/31 23:56
 * @Version 1.0
 */

@Service
public class UserServiceImpl extends BaseService implements UserService {
    @Override
    public boolean register(String username, String password, Integer sex) {
        User user = queryUserByUsername(username);
        //判空来保证用户名唯一性
        if (user==null){
            User newUser = new User(null, username, password, sex, ImageUtils.getRandomFace());
            Integer integer = userMapper.addUser(newUser);
            return integer == 1;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        User user1 = queryUserById(user.getId());
        if (user1==null){
            return false;
        }
        //判断是否更改了用户名，如果更改了用户名，则需要判断用户名是否重复
        if (!user.getUsername().equals(user1.getUsername())){
            User user2 = queryUserByUsername(user.getUsername());
            if (user2!=null){
                return false;
            }
            Integer integer = userMapper.updateUser(user);
            if (integer==1){
                redisService.del(UserKey.getById, user.getId()+"");
                redisService.del(UserKey.getByUsername, user1.getUsername());
                return true;
            }else {
                return false;
            }
        }else {
            Integer integer = userMapper.updateUser(user);
            if (integer==1){
                redisService.del(UserKey.getById, user.getId()+"");
                redisService.del(UserKey.getByUsername, user1.getUsername());
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean logoutById(Integer id) {
        User user = queryUserById(id);
        Integer integer = userMapper.deleteUserById(id);
        if (integer==1){
            redisService.del(UserKey.getById, id+"");
            redisService.del(UserKey.getById, user.getUsername());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User queryUserById(Integer id) {
        User user = redisService.get(UserKey.getById, id + "", User.class);
        if (user!=null){
            return user;
        }else {
            user = userMapper.queryUserById(id);
            if (user!=null){
                redisService.set(UserKey.getById, id+"", user);
                redisService.expire(UserKey.getById, id+"", 60*60*48);
            }
        }
        return user;
    }

    @Override
    public User queryUserByUsername(String username) {
        User user = redisService.get(UserKey.getByUsername, username, User.class);
        if (user!=null){
            return user;
        }else {
            user = userMapper.queryUserByUsername(username);
            if (user!=null){
                redisService.set(UserKey.getByUsername, username, user);
                redisService.expire(UserKey.getByUsername, username, 60*60*48);
            }
        }
        return user;
    }

}
