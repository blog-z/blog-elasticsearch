package com.elasticsearch.util;

import com.dubbo.commons.ServerResponse;
import com.dubbo.entity.Article;
import com.dubbo.util.DateTimeUtil;
import com.elasticsearch.config.ElasticsearchConfig;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class ElasticsearchUtil {

    private static final Logger logger= LoggerFactory.getLogger(ElasticsearchConfig.class);

    //发表文章
    public static ServerResponse setArticle(Article article){
        String articleJson="{" +
                "\"article_id\":\""+article.getArticleId()+"\"," +
                "\"article_user_id\":\""+article.getArticleUserId()+"\"," +
                "\"article_title\":\""+article.getArticleTitle()+"\"," +
                "\"article_content\":\""+article.getArticleContent()+"\"," +
                "\"create_time\":\""+ DateTimeUtil.dateToString(new Date()) +"\"," +
                "\"update_time\":\""+DateTimeUtil.dateToString(new Date())+"\""+
                "}";

        // JSON格式字符串
        HttpEntity entity = new NStringEntity(articleJson, ContentType.APPLICATION_JSON);
        try {
            ElasticsearchConfig.getLowRestClient().performRequest("post", "/" + "blog" +"/"+ "article" +"/"+article.getArticleId()+"/", Collections.EMPTY_MAP,entity);
        } catch (IOException e) {
            logger.info("插入数据失败！");
            e.printStackTrace();
            return ServerResponse.createByErrorCodeMessage(0,"插入数据失败");
        }
        return ServerResponse.createBySuccessMessage("插入数据成功！");
    }

    //删除文章
    public static ServerResponse deleteArticle(String articleId){
        try {
            ElasticsearchConfig.getLowRestClient().performRequest(new Request("delete","/blog/article/"+articleId));
        }catch (IOException e){
            logger.info("删除数据失败！");
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("删除数据失败！");
        }
        return ServerResponse.createByErrorMessage("删除数据成功！");
    }
}
