package com.kxt.supermarket.mapper;

import com.kxt.supermarket.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Kxt
 * @Date 2021/7/26 21:08
 * @Version 1.0
 */

@Mapper
public interface UserMapper {

    User queryUserById(Integer id);

    User queryUserByUsername(String username);

    Integer addUser(User user);

    Integer deleteUserById(Integer id);

    Integer updateUser(User user);
}
