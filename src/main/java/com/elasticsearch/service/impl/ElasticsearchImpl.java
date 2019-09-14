package com.elasticsearch.service.impl;

import com.dubbo.ElasticsearchService;
import com.dubbo.commons.ServerResponse;
import com.dubbo.entity.Article;
import com.dubbo.util.DateTimeUtil;
import com.elasticsearch.config.ElasticsearchConfig;
import com.elasticsearch.util.ElasticsearchUtil;
import org.apache.dubbo.config.annotation.Service;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

/**
 * @program: blog-elasticsearch
 * @description: 实现dubbo方法
 * @author: qingchun
 * @create: 2019-09-09 12:56
 **/

@Service(version = "${dubbo.provider.version}")
public class ElasticsearchImpl implements ElasticsearchService {


    private static final Logger logger= LoggerFactory.getLogger(ElasticsearchImpl.class);

    //更新文章
    @Override
    public ServerResponse updateArticle(Article article) {
        String articleJson="{" +
                "\"article_id\":\""+article.getArticleId()+"\"," +
                "\"article_user_id\":\""+article.getArticleUserId()+"\"," +
                "\"article_title\":\""+article.getArticleTitle()+"\"," +
                "\"article_content\":\""+article.getArticleContent()+"\"," +
                "\"create_time\":\""+ DateTimeUtil.dateToString(new Date()) +"\"," +
                "\"update_time\":\""+DateTimeUtil.dateToString(new Date())+"\""+
                "}";
        HttpEntity httpEntity=new NStringEntity(articleJson, ContentType.APPLICATION_JSON);
        try {
            ElasticsearchConfig.getLowRestClient().performRequest("post","/" + "blog" +"/"+ "article" +"/"+article.getArticleId()+"/", Collections.EMPTY_MAP,httpEntity);
        } catch (IOException e) {
            logger.info("更新文章失败！");
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新文章失败");
        }
        return ServerResponse.createBySuccessMessage("更新文章成功");
    }

    @Override
    public String sayHello(String s) {
        return s+"qingchun";
    }

    //新增文章
    @Override
    public ServerResponse addArticle(Article article) {
        return ElasticsearchUtil.setArticle(article);
    }

    //查询文章
    @Override
    public ServerResponse selectArticle(String userId, String articleText) {
        return null;
    }

    //删除文章
    @Override
    public ServerResponse deleteArticle(String articleId) {
        return ElasticsearchUtil.deleteArticle(articleId);
    }

}