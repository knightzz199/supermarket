package com.kxt.supermarket.controller;

import com.kxt.supermarket.Utils.JSONUtils;
import com.kxt.supermarket.Utils.Result;
import com.kxt.supermarket.pojo.UserCollection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/3 15:38
 * @Version 1.0
 */

@RestController
@RequestMapping("/userCollection")
public class UserCollectionController extends BaseController{
    @GetMapping("/getUserCollectionById")
    public String getUserCollectionById(Integer id){
        UserCollection userCollection = userCollectionService.queryUserCollectionById(id);
        return JSONUtils.objectToJson(Result.success(userCollection));
    }
    @GetMapping("/getUserCollectionByUid")
    public String getUserCollectionByUid(Integer uid){
        List<UserCollection> userCollections = userCollectionService.queryUserCollectionByUid(uid);
        return JSONUtils.objectToJson(Result.success(userCollections));
    }
    @PostMapping("/addUserCollection")
    public String addUserCollection(Integer uid, Integer gid){
        boolean b = userCollectionService.addUserCollection(uid, gid);
        return JSONUtils.objectToJson(Result.success(b));
    }
    @GetMapping("/deleteUserCollectionById")
    public String deleteUserCollectionById(Integer id){
        boolean b = userCollectionService.deleteUserCollectionById(id);
        return JSONUtils.objectToJson(Result.success(b));
    }
    @PostMapping("/updateUserCollection")
    public String updateUserCollection(UserCollection userCollection){
        boolean b = userCollectionService.updateUserCollection(userCollection);
        return JSONUtils.objectToJson(Result.success(b));
    }
}
