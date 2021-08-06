package com.kxt.supermarket.redis;

/**
 * @Author Kxt
 * @Date 2021/8/5 0:02
 * @Version 1.0
 */
public class SecKillKey extends BasePrefix{
    private SecKillKey(String prefix) {
        super(prefix);
    }
    public static SecKillKey getByUid = new SecKillKey("uid");
    public static SecKillKey getByStack = new SecKillKey("stack");
}
