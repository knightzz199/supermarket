package com.kxt.supermarket.Utils;

/**
 * @Author Kxt
 * @Date 2021/7/27 20:40
 * @Version 1.0
 * 用于返回结果集
 */

public class Result {
    private int code;
    private Object data;
    private String message;

    private static final String SUCCESS_MESSAGE="success";
    private static final String ERROR_MESSAGE="error";
    private static final int SUCCESS_CODE=200;
    private static final int ERROR_CODE=500;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static Result success(Object data){
        return new Result(SUCCESS_CODE, data, SUCCESS_MESSAGE);
    }

    public static Result error(Object data){
        return new Result(ERROR_CODE, data, ERROR_MESSAGE);
    }

}
