package com.kxt.supermarket.controller;

import com.kxt.supermarket.Utils.JSONUtils;
import com.kxt.supermarket.Utils.Result;
import com.kxt.supermarket.pojo.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Kxt
 * @Date 2021/8/3 15:12
 * @Version 1.0
 */

@RestController
@RequestMapping("/type")
public class TypeController extends BaseController{
    @PostMapping("/addType")
    public String addType(String name){
        boolean b = typeService.addType(name);
        return JSONUtils.objectToJson(Result.success(b));
    }
    @GetMapping("/deleteType")
    public String deleteType(Integer id){
        boolean b = typeService.deleteTypeById(id);
        return JSONUtils.objectToJson(Result.success(b));
    }
    @PostMapping("/updateType")
    public String updateType(Type type){
        boolean b = typeService.updateType(type);
        return JSONUtils.objectToJson(Result.success(b));
    }
    @GetMapping("/queryTypeById")
    public String queryTypeById(Integer id){
        Type type = typeService.queryTypeById(id);
        return JSONUtils.objectToJson(Result.success(type));
    }
    @GetMapping("/queryTypeByName")
    public String queryTypeByName(String name){
        Type type = typeService.queryTypeByName(name);
        return JSONUtils.objectToJson(Result.success(type));
    }
}
