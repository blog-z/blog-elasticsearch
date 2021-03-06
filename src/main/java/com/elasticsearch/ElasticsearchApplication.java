package com.elasticsearch;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @program: blog-elasticsearch
 * @description: 启动类
 * @author: qingchun
 * @create: 2019-09-08 21:37
 **/
@SpringBootApplication
@EnableDubbo
public class ElasticsearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchApplication.class,args);
    }
}
