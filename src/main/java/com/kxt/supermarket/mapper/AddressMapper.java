package com.kxt.supermarket.mapper;

import com.kxt.supermarket.pojo.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author Kxt
 * @Date 2021/7/26 22:44
 * @Version 1.0
 */

@Mapper
public interface AddressMapper {
    Address queryAddressById(Integer id);

    List<Address> queryAddressByUid(Integer uid);

    Integer addAddress(Address address);

    Integer deleteAddressById(Integer id);

    Integer updateAddress(Address address);
}
