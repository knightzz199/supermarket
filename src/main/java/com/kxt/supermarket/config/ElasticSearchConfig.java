package com.kxt.supermarket.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
/**
 * @Author Kxt
 * @Date 2021/7/26 19:49
 * @Version 1.0
 *
 * 配置elasticsearch
 */

@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        // 定义客户端配置对象 9200端口
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("www.xtnb.xyz:9200")
                .withBasicAuth("elastic", "*********")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
