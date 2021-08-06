package com.kxt.supermarket.service.impl;

import com.kxt.supermarket.Utils.ImageUtils;
import com.kxt.supermarket.pojo.Admin;
import com.kxt.supermarket.redis.AdminKey;
import com.kxt.supermarket.service.AdminService;
import com.kxt.supermarket.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * @Author Kxt
 * @Date 2021/7/31 0:55
 * @Version 1.0
 */
@Service
public class AdminServiceImpl extends BaseService implements AdminService {
    @Override
    public boolean register(String username, String password, Integer sex) {
        Admin admin = queryAdminByUsername(username);
        //判空来保证用户名唯一性
        if (admin==null){
            Admin newAdmin = new Admin(null, username, password, sex, ImageUtils.getRandomFace());
            Integer integer = adminMapper.addAdmin(newAdmin);
            return integer == 1;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(Admin admin) {
        Admin admin1 = queryAdminById(admin.getId());
        if(admin1==null){
            return false;
        }
        //判断是否更改了用户名，如果更改了用户名，则需要判断用户名是否重复
        if (!admin.getUsername().equals(admin1.getUsername())){
            Admin admin2 = queryAdminByUsername(admin.getUsername());
            //在用户名不同，即更改了用户名的情况下，判断一下是否有相同用户名的账号存在
            if (admin2!=null){
                return false;
            }
            Integer integer = adminMapper.updateAdmin(admin);
            if (integer==1){
                redisService.del(AdminKey.getById, admin.getId()+"");
                redisService.del(AdminKey.getByUsername, admin1.getUsername());
                return true;
            }else {
                return false;
            }
        }else {
            Integer integer = adminMapper.updateAdmin(admin);
            if (integer==1){
                redisService.del(AdminKey.getById, admin.getId()+"");
                redisService.del(AdminKey.getByUsername, admin1.getUsername());
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean logoutById(Integer id) {
        Admin admin = queryAdminById(id);
        Integer integer = adminMapper.deleteAdminById(id);
        if (integer==1){
            redisService.del(AdminKey.getById, id+"");
            redisService.del(AdminKey.getByUsername, admin.getUsername());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Admin queryAdminById(Integer id) {
        Admin admin = redisService.get(AdminKey.getById, id + "", Admin.class);
        if (admin!=null){
            return admin;
        }else {
            admin = adminMapper.queryAdminById(id);
            if (admin!=null){
                redisService.set(AdminKey.getById, id+"", admin);
                redisService.expire(AdminKey.getById, id+"", 60*60*48);
            }
        }
        return admin;
    }

    @Override
    public Admin queryAdminByUsername(String username) {
        Admin admin = redisService.get(AdminKey.getByUsername, username, Admin.class);
        if (admin!=null){
            return admin;
        }else {
            admin = adminMapper.queryAdminByUsername(username);
            if (admin!=null){
                redisService.set(AdminKey.getByUsername, username, admin);
                redisService.expire(AdminKey.getByUsername, username, 60*60*48);
            }
        }
        return admin;
    }

}
