package com.kxt.supermarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author Kxt
 * @Date 2021/7/26 19:52
 * @Version 1.0
 *
 * 配置swagger接口文档
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    //注册一个Docket实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        Contact contact=new Contact("kxt", "http://www.xtnb.xyz","1176305911@qq.com" );

        return new ApiInfo("kxt的SwaggerAPI文档", "起飞", "v1.0", "http://www.xtnb.xyz",
                contact, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }
}