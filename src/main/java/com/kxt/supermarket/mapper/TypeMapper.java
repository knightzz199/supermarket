package com.kxt.supermarket.mapper;

import com.kxt.supermarket.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Kxt
 * @Date 2021/7/26 23:57
 * @Version 1.0
 */

@Mapper
public interface TypeMapper {
    Type queryTypeById(Integer id);
    Type queryTypeByName(String name);
    Integer deleteTypeById(Integer id);
    Integer addType(Type type);
    Integer updateType(Type type);
}
