package com.kxt.supermarket.redis;

/**
 * @Author Kxt
 * @Date 2021/7/27 22:49
 * @Version 1.0
 */

public abstract class BasePrefix implements KeyPrefix{
    private String prefix;

    public BasePrefix(String prefix){
        this.prefix = prefix;
    }

    @Override
    public String getPrefix(){
        String simpleName = this.getClass().getSimpleName();
        return simpleName + ":" + prefix;
    }
}
