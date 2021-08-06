package com.kxt.supermarket.redis;

/**
 * @Author Kxt
 * @Date 2021/7/27 23:05
 * @Version 1.0
 */

public class UserCollectionKey extends BasePrefix{
    private UserCollectionKey(String prefix) {
        super(prefix);
    }
    public static UserCollectionKey getById = new UserCollectionKey("id");
    public static UserCollectionKey getByUid = new UserCollectionKey("user_id");
}
