package com.kxt.supermarket.redis;

/**
 * @Author Kxt
 * @Date 2021/7/27 23:02
 * @Version 1.0
 */

public class AdminKey extends BasePrefix{
    private AdminKey(String prefix) {
        super(prefix);
    }
    public static AdminKey getById = new AdminKey("id");
    public static AdminKey getByUsername = new AdminKey("username");
}
