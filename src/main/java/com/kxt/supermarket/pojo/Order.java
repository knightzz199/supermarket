package com.kxt.supermarket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author Kxt
 * @Date 2021/7/26 18:40
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;
    //购买总量
    private Integer num;
    //商品总价
    private Double price;
    //邮费
    private Double expressFee;
    private Integer goodsId;
    private Integer userId;
    private Integer addressId;
    //订单状态，0未付款，1已付款，2表示未发货，3表示已发货，4表示已完成
    private Integer state;
    //发起订单日期
    private Date createTime;
    //总价
    private Double totalPrice;
    //商家id
    private Integer adminId;
    //订单号,用uuid产生
    private String orderNum;
}
