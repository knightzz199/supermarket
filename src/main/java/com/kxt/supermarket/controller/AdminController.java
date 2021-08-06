package com.kxt.supermarket.controller;

import com.kxt.supermarket.Utils.ImageUtils;
import com.kxt.supermarket.Utils.JSONUtils;
import com.kxt.supermarket.Utils.Result;
import com.kxt.supermarket.pojo.Admin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author Kxt
 * @Date 2021/8/2 22:36
 * @Version 1.0
 */

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
    //商家注册账号
    @PostMapping("/register")
    public String register(String username,String password,Integer sex){
        boolean b = adminService.register(username, password, sex);
        Result success = Result.success(b);
        return JSONUtils.objectToJson(success);
    }
    //商家登录
    @GetMapping("/login")
    public String login(String username,String password){
        Admin admin = adminService.queryAdminByUsername(username);
        if (admin!=null&&admin.getPassword().equals(password)){
            session.setAttribute("admin", admin);
            Result success = Result.success(admin);
            return JSONUtils.objectToJson(success);
        }else {
            return JSONUtils.objectToJson(Result.success(false));
        }
    }
    //修改商家信息
    @PostMapping("/update")
    public String update(Admin admin){
        boolean b = adminService.update(admin);
        if (b){
            Admin admin1 = adminService.queryAdminById(admin.getId());
            session.setAttribute("admin", admin1);
            return JSONUtils.objectToJson(Result.success(admin1));
        }else {
            return JSONUtils.objectToJson(Result.success(false));
        }
    }
    //商家注销账号
    @GetMapping("/logout")
    public String logout(Integer id){
        boolean b = adminService.logoutById(id);
        return JSONUtils.objectToJson(Result.success(b));
    }
    //商家更换头像
    @PostMapping("/changeHead")
    public String changeHead(Integer id, MultipartFile file) throws IOException {
        Admin admin = adminService.queryAdminById(id);
        String headUrl = ImageUtils.upload(file);
        if (headUrl!=null){
            admin.setPicUrl(headUrl);
            return update(admin);
        }else {
            return JSONUtils.objectToJson(false);
        }
    }
}
