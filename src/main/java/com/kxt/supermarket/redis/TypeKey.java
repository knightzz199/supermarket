package com.kxt.supermarket.redis;

/**
 * @Author Kxt
 * @Date 2021/7/27 23:04
 * @Version 1.0
 */

public class TypeKey extends BasePrefix{
    private TypeKey(String prefix) {
        super(prefix);
    }
    public static TypeKey getById = new TypeKey("id");
    public static TypeKey getByName = new TypeKey("name");
}
