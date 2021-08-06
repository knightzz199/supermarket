package com.kxt.supermarket.mapper;

import com.kxt.supermarket.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Kxt
 * @Date 2021/7/26 22:28
 * @Version 1.0
 */

@Mapper
public interface AdminMapper {
    Admin queryAdminById(Integer id);

    Admin queryAdminByUsername(String username);

    Integer addAdmin(Admin admin);

    Integer updateAdmin(Admin admin);

    Integer deleteAdminById(Integer id);
}
