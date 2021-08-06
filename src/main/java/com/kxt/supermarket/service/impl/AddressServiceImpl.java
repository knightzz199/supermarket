package com.kxt.supermarket.service.impl;

import com.kxt.supermarket.pojo.Address;
import com.kxt.supermarket.redis.AddressKey;
import com.kxt.supermarket.service.AddressService;
import com.kxt.supermarket.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/8/1 17:04
 * @Version 1.0
 */

@Service
public class AddressServiceImpl extends BaseService implements AddressService {
    @Override
    public Address queryAddressById(Integer id) {
        Address address = redisService.get(AddressKey.getById, id + "", Address.class);
        if (address!=null){
            return address;
        }else {
            address = addressMapper.queryAddressById(id);
            if (address!=null){
                redisService.set(AddressKey.getById, id+"", address);
                redisService.expire(AddressKey.getById, id+"", 60*60*24);
                return address;
            }else {
                return null;
            }
        }
    }

    @Override
    public List<Address> queryAddressByUid(Integer uid) {
        List<Address> addressList = redisService.getList(AddressKey.getByUid, uid + "", Address.class);
        if (addressList!=null){
            return addressList;
        }else {
            addressList = addressMapper.queryAddressByUid(uid);
            if (addressList!=null){
                redisService.setList(AddressKey.getByUid, uid+"", addressList);
                redisService.expire(AddressKey.getByUid, uid+"",60*60*24);
                return addressList;
            }else {
                return null;
            }
        }
    }

    @Override
    public boolean addAddress(String name,String phone,String addr,Integer uid) {
        Address address = new Address(null, name, phone, addr, uid);
        Integer integer = addressMapper.addAddress(address);
        return integer == 1;
    }

    @Override
    public boolean deleteAddressById(Integer id) {
        Address address = queryAddressById(id);
        Integer integer = addressMapper.deleteAddressById(id);
        if (integer==1){
            redisService.del(AddressKey.getById, id+"");
            redisService.del(AddressKey.getByUid,address.getUserId()+"");
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateAddress(Address address) {
        Integer integer = addressMapper.updateAddress(address);
        if (integer==1){
            redisService.del(AddressKey.getById, address.getId()+"");
            redisService.del(AddressKey.getByUid, address.getUserId()+"");
            return true;
        }else {
            return false;
        }
    }
}
