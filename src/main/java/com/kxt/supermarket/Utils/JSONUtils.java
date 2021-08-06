package com.kxt.supermarket.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @Author Kxt
 * @Date 2021/7/27 20:44
 * @Version 1.0
 */

public class JSONUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        // 对于空的对象转json的时候不抛出错误
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 允许属性名称没有引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 设置输入时忽略在json字符串中存在但在java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 设置输出时包含属性的风格
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    //javabean转Json
    public static String objectToJson(Object data) {
        if (data == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Bean转Json
    public static <T> T jsonToBean(String jsonData, Class<T> beanType) {
        if(jsonData==null){
            return null;
        }
        try {
            return mapper.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
