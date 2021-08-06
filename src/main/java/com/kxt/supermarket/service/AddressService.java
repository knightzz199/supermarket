package com.kxt.supermarket.service;

import com.kxt.supermarket.pojo.Address;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/1 17:03
 * @Version 1.0
 */

public interface AddressService {
    Address queryAddressById(Integer id);

    List<Address> queryAddressByUid(Integer uid);

    boolean addAddress(String name,String phone,String addr,Integer uid);

    boolean deleteAddressById(Integer id);

    boolean updateAddress(Address address);
}
