package com.elasticsearch.service.impl;

import com.dubbo.ElasticsearchService;
import com.dubbo.commons.ServerResponse;
import com.dubbo.entity.Article;
import com.elasticsearch.util.ElasticsearchUtil;
import org.apache.dubbo.config.annotation.Service;

/**
 * @program: blog-elasticsearch
 * @description: 实现dubbo方法
 * @author: qingchun
 * @create: 2019-09-09 12:56
 **/

@Service(version = "${dubbo.provider.version}")
public class ElasticsearchImpl implements ElasticsearchService {

    //搜索文章
    @Override
    public ServerResponse searchArticle(String userInputText,int pageNum) {
        return ElasticsearchUtil.searchElasticsearch(userInputText,pageNum);
    }

    //更新文章
    @Override
    public ServerResponse updateArticle(Article article) {
        return ElasticsearchUtil.updateElasticsearch(article);
    }


    //新增文章
    @Override
    public ServerResponse addArticle(Article article) {
        return ElasticsearchUtil.indexElasticsearch(article);
    }

    //查询文章
    @Override
    public ServerResponse selectArticle(String articleId) {
        return ElasticsearchUtil.getElasticsearch(articleId);
    }

    //删除文章
    @Override
    public ServerResponse deleteArticle(String articleId) {
        return ElasticsearchUtil.deleteElasticsearch(articleId);
    }

}