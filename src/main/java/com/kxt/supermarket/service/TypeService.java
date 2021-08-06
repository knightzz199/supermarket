package com.kxt.supermarket.service;

import com.kxt.supermarket.pojo.Type;

/**
 * @Author Kxt
 * @Date 2021/8/1 10:44
 * @Version 1.0
 */

public interface TypeService {
    boolean deleteTypeById(Integer id);
    boolean addType(String name);
    boolean updateType(Type type);
    Type queryTypeById(Integer id);
    Type queryTypeByName(String name);
}
