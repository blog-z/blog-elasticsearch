package com.elasticsearch.service.impl;

import com.dubbo.ElasticsearchService;
import com.dubbo.commons.ServerResponse;
import com.dubbo.entity.Article;
import com.elasticsearch.util.ElasticsearchUtil;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

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

    //查看自己已发表的文章
    public ServerResponse getOwnArticle(List<String> articleIdList){
        return ElasticsearchUtil.getOwnArticle(articleIdList);
    }

    //删除文章
    @Override
    public ServerResponse deleteArticle(String articleId) {
        return ElasticsearchUtil.deleteElasticsearch(articleId);
    }

    //增加文章热点
    public ServerResponse updateArticleHeat(String articleId,Integer articleHeat){
        return ElasticsearchUtil.updateElasticsearchArticleHeat(articleId,articleHeat);
    }

    //首页推荐
    public ServerResponse homeArticleInteger(Integer pageNum){
        return ElasticsearchUtil.homeSearchElasticsearch(pageNum);
    }

    //得到文章标题
    public String getArticleTitle(String articleId){
        return ElasticsearchUtil.getArticleTitle(articleId);
    }

}