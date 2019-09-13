package com.elasticsearch.service.impl;

import com.dubbo.ElasticsearchService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @program: blog-elasticsearch
 * @description: 实现dubbo方法
 * @author: qingchun
 * @create: 2019-09-09 12:56
 **/

@Service(version = "${dubbo.provider.version}")
public class ElasticsearchImpl implements ElasticsearchService {


    @Override
    public String sayHello(String s) {
        return "zhu"+s;
    }
}