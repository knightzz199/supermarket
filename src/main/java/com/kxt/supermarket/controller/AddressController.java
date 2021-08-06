package com.kxt.supermarket.controller;

import com.kxt.supermarket.Utils.JSONUtils;
import com.kxt.supermarket.Utils.Result;
import com.kxt.supermarket.pojo.Address;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/3 14:47
 * @Version 1.0
 */

@RestController
@RequestMapping("/address")
public class AddressController extends BaseController{
    @PostMapping("/addAddress")
    public String addAddress(String name,String phone,String addr,Integer uid){
        boolean b = addressService.addAddress(name, phone, addr, uid);
        return JSONUtils.objectToJson(Result.success(b));
    }
    @PostMapping("/updateAddress")
    public String updateAddress(Address address){
        boolean b = addressService.updateAddress(address);
        return JSONUtils.objectToJson(Result.success(b));
    }
    @GetMapping("/deleteAddress")
    public String deleteAddress(Integer id){
        boolean b = addressService.deleteAddressById(id);
        return JSONUtils.objectToJson(Result.success(b));
    }
    @GetMapping("/getUserAllAddress")
    public String getUserAllAddress(Integer uid){
        List<Address> addresses = addressService.queryAddressByUid(uid);
        return JSONUtils.objectToJson(Result.success(addresses));
    }
    @GetMapping("/getAddressById")
    public String getAddressById(Integer id){
        Address address = addressService.queryAddressById(id);
        return JSONUtils.objectToJson(Result.success(address));
    }
}
