package com.kxt.supermarket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Kxt
 * @Date 2021/7/26 18:28
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer id;
    private String username;
    private String password;
    private Integer sex;
    private String picUrl;
}
