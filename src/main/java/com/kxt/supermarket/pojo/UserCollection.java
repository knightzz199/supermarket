package com.kxt.supermarket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Kxt
 * @Date 2021/7/26 18:32
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCollection {
    private Integer id;
    private Integer userId;
    private Integer goodsId;
}
