package com.kxt.supermarket.service;

import com.kxt.supermarket.pojo.User;

/**
 * @Author Kxt
 * @Date 2021/7/31 23:55
 * @Version 1.0
 */

public interface UserService {
    boolean register(String username,String password,Integer sex);

    boolean update(User user);

    boolean logoutById(Integer id);

    User queryUserById(Integer id);

    User queryUserByUsername(String username);
}
