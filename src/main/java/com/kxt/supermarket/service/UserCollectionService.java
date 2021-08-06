package com.kxt.supermarket.service;

import com.kxt.supermarket.pojo.UserCollection;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/1 17:28
 * @Version 1.0
 */

public interface UserCollectionService {
    UserCollection queryUserCollectionById(Integer id);

    List<UserCollection> queryUserCollectionByUid(Integer uid);

    boolean addUserCollection(Integer uid,Integer gid);

    boolean deleteUserCollectionById(Integer id);

    boolean updateUserCollection(UserCollection userCollection);
}
