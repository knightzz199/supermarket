package com.kxt.supermarket.redis;

/**
 * @Author Kxt
 * @Date 2021/8/1 17:06
 * @Version 1.0
 */
public class AddressKey extends BasePrefix{
    private AddressKey(String prefix) {
        super(prefix);
    }
    public static AddressKey getById = new AddressKey("id");
    public static AddressKey getByUid = new AddressKey("uid");
}
