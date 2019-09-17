package com.elasticsearch.util;

import com.dubbo.commons.ServerResponse;
import com.dubbo.entity.Article;
import com.dubbo.util.DateTimeUtil;
import com.dubbo.util.JsonUtil;
import com.elasticsearch.SerializableElasticsearch.GetResponseSerializable;
import com.elasticsearch.SerializableElasticsearch.IndexResponseSerializable;
import com.elasticsearch.config.ElasticsearchConfig;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ElasticsearchUtil{

    private static final Logger logger= LoggerFactory.getLogger(ElasticsearchUtil.class);


    //IndexAPI
    public static ServerResponse indexElasticsearch(Article article){
        IndexResponse response = null;
        try {
            response = ElasticsearchConfig.getTransportClient().prepareIndex("blog", "article", article.getArticleId())
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("article_id", article.getArticleId())
                            .field("article_user_id", article.getArticleUserId())
                            .field("article_title", article.getArticleTitle())
                            .field("article_content", article.getArticleContent())
                            .field("create_time", DateTimeUtil.dateToString(new Date()))
                            .field("update_time", DateTimeUtil.dateToString(new Date()))
                            .endObject()
                    )
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response!=null&&response.getShardInfo().getSuccessful()>0){
            IndexResponseSerializable indexResponseSerializable =(IndexResponseSerializable)response;
            return ServerResponse.createBySuccess("创建索引成功", indexResponseSerializable);
        }else {
            return ServerResponse.createByErrorMessage("创建索引失败");
        }
    }

    //GET API
    public static ServerResponse getElasticsearch(String articleId){
        GetResponse response = ElasticsearchConfig.getTransportClient().prepareGet("blog", "article", articleId).get();
        return ServerResponse.createBySuccess("查询索引成功");
    }

    //DELETE AIP
    public static ServerResponse deleteElasticsearch(String articleId){
        DeleteResponse response = ElasticsearchConfig.getTransportClient().prepareDelete("blog", "article", articleId).get();

        return ServerResponse.createBySuccess("删除索引成功",JsonUtil.objToStringPretty(response));
    }

    //UPDATE API
    public static ServerResponse updateElasticsearch(Article article){
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("blog");
        updateRequest.type("article");
        updateRequest.id(article.getArticleId());
        try {
            updateRequest.doc(jsonBuilder()
                    .startObject()
                    .field("article_id", article.getArticleId())
                    .field("article_user_id", article.getArticleUserId())
                    .field("article_title", article.getArticleTitle())
                    .field("article_content",article.getArticleContent())
                    .field("create_time",DateTimeUtil.dateToString(new Date()))
                    .field("update_time",DateTimeUtil.dateToString(new Date()))
                    .endObject());
            ElasticsearchConfig.getTransportClient().update(updateRequest).get();
        }  catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
            logger.info("-------更新失败------");
        }
        return ServerResponse.createBySuccessMessage("更新成功");
    }

    //search API
    public static ServerResponse searchElasticsearch(String userInputText){
        return null;
    }

}





