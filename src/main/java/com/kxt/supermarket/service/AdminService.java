package com.kxt.supermarket.service;

import com.kxt.supermarket.pojo.Admin;

/**
 * @Author Kxt
 * @Date 2021/7/31 0:44
 * @Version 1.0
 */

public interface AdminService{
    boolean register(String username,String password,Integer sex);

    boolean update(Admin admin);

    boolean logoutById(Integer id);

    Admin queryAdminById(Integer id);

    Admin queryAdminByUsername(String username);
}
