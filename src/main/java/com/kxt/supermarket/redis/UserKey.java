package com.kxt.supermarket.redis;

/**
 * @Author Kxt
 * @Date 2021/7/27 22:52
 * @Version 1.0
 */

public class UserKey extends BasePrefix{

    private UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey getById = new UserKey("id");
    public static UserKey getByUsername = new UserKey("username");
}
