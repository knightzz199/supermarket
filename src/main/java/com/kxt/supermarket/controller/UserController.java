package com.kxt.supermarket.controller;

import com.kxt.supermarket.Utils.ImageUtils;
import com.kxt.supermarket.Utils.JSONUtils;
import com.kxt.supermarket.Utils.Result;
import com.kxt.supermarket.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author Kxt
 * @Date 2021/8/2 23:01
 * @Version 1.0
 */

@RestController
@RequestMapping(("/user"))
public class UserController extends BaseController{
    //用户注册账号
    @PostMapping("/register")
    public String register(String username,String password,Integer sex){
        boolean b = userService.register(username, password, sex);
        Result success = Result.success(b);
        return JSONUtils.objectToJson(success);
    }
    //用户登录
    @GetMapping("/login")
    public String login(String username,String password){
        User user = userService.queryUserByUsername(username);
        if (user!=null&&user.getPassword().equals(password)){
            session.setAttribute("user", user);
            Result success = Result.success(user);
            return JSONUtils.objectToJson(success);
        }else {
            return JSONUtils.objectToJson(Result.success(false));
        }
    }
    //修改用户信息
    @PostMapping("/update")
    public String update(User user){
        boolean b = userService.update(user);
        if (b){
            User user1 = userService.queryUserById(user.getId());
            session.setAttribute("user", user1);
            return JSONUtils.objectToJson(Result.success(user1));
        }else {
            return JSONUtils.objectToJson(Result.success(false));
        }
    }
    //用户注销账号
    @GetMapping("/logout")
    public String logout(Integer id){
        boolean b = userService.logoutById(id);
        return JSONUtils.objectToJson(Result.success(b));
    }
    //用户更换头像
    @PostMapping("/changeHead")
    public String changeHead(Integer id,MultipartFile file) throws IOException {
        User user = userService.queryUserById(id);
        String headUrl = ImageUtils.upload(file);
        if (headUrl!=null){
            user.setPicUrl(headUrl);
            return update(user);
        }else {
            return JSONUtils.objectToJson(false);
        }
    }
}
