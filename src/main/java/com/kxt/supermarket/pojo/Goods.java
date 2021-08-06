package com.kxt.supermarket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @Author Kxt
 * @Date 2021/7/26 18:33
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "supermarket")
public class Goods implements Serializable {
    //该类需要实现序列化，便于使用rabbitmq作为传输的消息
    @Id
    private Integer id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String goodsDescription;
    @Field(type = FieldType.Keyword)
    private String picUrl;
    //库存
    @Field(type = FieldType.Integer)
    private Integer stock;
    //状态 0:下架 1:可以购买 2:卖完了
    @Field(type = FieldType.Keyword)
    private Integer state;
    @Field(type = FieldType.Integer)
    private Integer saleNum;
    @Field(type = FieldType.Double)
    private Double goodsPrice;
    @Field(type = FieldType.Integer)
    private Integer adminId;
    @Field(type = FieldType.Integer)
    private Integer typeId;
}
