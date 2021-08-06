package com.kxt.supermarket.mapper;

import com.kxt.supermarket.pojo.UserCollection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/7/26 23:42
 * @Version 1.0
 */

@Mapper
public interface UserCollectionMapper {
    UserCollection queryUserCollectionById(Integer id);

    List<UserCollection> queryUserCollectionByUid(Integer uid);

    Integer addUserCollection(UserCollection userCollection);

    Integer deleteUserCollectionById(Integer id);

    Integer updateUserCollection(UserCollection userCollection);
}
