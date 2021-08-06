package com.kxt.supermarket.elasticsearch;

import com.kxt.supermarket.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author Kxt
 * @Date 2021/7/30 23:57
 * @Version 1.0
 */

public interface GoodsRepository extends ElasticsearchRepository<Goods,Integer> {
}
