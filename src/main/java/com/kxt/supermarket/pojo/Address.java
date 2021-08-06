package com.kxt.supermarket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Kxt
 * @Date 2021/7/26 18:24
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private Integer id;
    private String name;
    private String phone;
    private String addr;
    private Integer userId;
}
