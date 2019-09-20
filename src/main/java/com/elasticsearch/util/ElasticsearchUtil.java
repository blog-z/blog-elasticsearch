package com.elasticsearch.util;

import com.dubbo.commons.Page;
import com.dubbo.commons.SearchElasticsearchArticleJson;
import com.dubbo.commons.ServerResponse;
import com.dubbo.entity.Article;
import com.dubbo.util.DateTimeUtil;
import com.dubbo.util.JsonUtil;
import com.elasticsearch.SerializableElasticsearch.*;
import com.elasticsearch.config.ElasticsearchConfig;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

public class ElasticsearchUtil{

    //IndexAPI
    public static ServerResponse indexElasticsearch(Article article){
        IndexResponse response = null;
        try {
            response = ElasticsearchConfig.getTransportClient().prepareIndex("blog", "articles", article.getArticleId())
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
            ElasticsearchResult elasticsearchResult=new ElasticsearchResult(response.getResult().getOp(),response.getResult().getLowercase(),response.getResult().name(),response.getResult().ordinal());
            ElasticsearchShardInfo elasticsearchShardInfo=new ElasticsearchShardInfo(response.getShardInfo().getTotal(),response.getShardInfo().getSuccessful(),response.getShardInfo().getFailures());
            ElasticsearchResponse elasticsearchResponse=new ElasticsearchResponse(
                    response.getId(),
                    response.getType(),
                    response.getVersion(),
                    elasticsearchResult,
                    elasticsearchShardInfo
            );
            return ServerResponse.createBySuccess("创建索引成功",elasticsearchResponse);
        }else {
            return ServerResponse.createByErrorMessage("创建索引失败");
        }
    }

    //GET API
    public static ServerResponse getElasticsearch(String articleId){
        GetResponse response = ElasticsearchConfig.getTransportClient().prepareGet("blog", "articles", articleId).get();
        if (response!=null){
            ElasticsearchResponse elasticsearchResponse=new ElasticsearchResponse(
                    response.getId(),
                    response.getType(),
                    response.getVersion(),
                    response.getSource(),
                    null
            );
            return ServerResponse.createBySuccess("GET索引成功",elasticsearchResponse);
        }else {
            return ServerResponse.createByErrorMessage("GET索引失败");
        }
    }

    //DELETE AIP
    public static ServerResponse deleteElasticsearch(String articleId){
        DeleteResponse response = ElasticsearchConfig.getTransportClient().prepareDelete("blog", "articles", articleId).get();
        if (response!=null&&response.getShardInfo().getSuccessful()>0){
            ElasticsearchResult elasticsearchResult=new ElasticsearchResult(response.getResult().getOp(),response.getResult().getLowercase(),response.getResult().name(),response.getResult().ordinal());
            ElasticsearchShardInfo elasticsearchShardInfo=new ElasticsearchShardInfo(response.getShardInfo().getTotal(),response.getShardInfo().getSuccessful(),response.getShardInfo().getFailures());
            ElasticsearchResponse elasticsearchResponse=new ElasticsearchResponse(
                    response.getId(),
                    response.getType(),
                    response.getVersion(),
                    elasticsearchResult,
                    elasticsearchShardInfo
            );
            return ServerResponse.createBySuccess("删除索引成功",elasticsearchResponse);
        }else {
            return ServerResponse.createByErrorMessage("删除索引失败");
        }
    }

    //UPDATE API
    public static ServerResponse updateElasticsearch(Article article){
        UpdateRequest updateRequest = new UpdateRequest();
        UpdateResponse response=null;
        updateRequest.index("blog");
        updateRequest.type("articles");
        updateRequest.id(article.getArticleId());
        try {
            updateRequest.doc(jsonBuilder()
                    .startObject()
                    .field("article_title", article.getArticleTitle())
                    .field("article_content",article.getArticleContent())
                    .field("update_time",DateTimeUtil.dateToString(new Date()))
                    .endObject());
            response=ElasticsearchConfig.getTransportClient().update(updateRequest).get();
        }  catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
        }
        if (response!=null&&response.getShardInfo().getSuccessful()>0){
            ElasticsearchResult elasticsearchResult=new ElasticsearchResult(response.getResult().getOp(),response.getResult().getLowercase(),response.getResult().name(),response.getResult().ordinal());
            ElasticsearchShardInfo elasticsearchShardInfo=new ElasticsearchShardInfo(response.getShardInfo().getTotal(),response.getShardInfo().getSuccessful(),response.getShardInfo().getFailures());
            ElasticsearchResponse elasticsearchResponse=new ElasticsearchResponse(
                    response.getId(),
                    response.getType(),
                    response.getVersion(),
                    elasticsearchResult,
                    elasticsearchShardInfo
            );
            return ServerResponse.createBySuccess("更新文档成功",elasticsearchResponse);
        }else {
            return ServerResponse.createByErrorMessage("更新文档失败");
        }
    }

    //search API （1）
    public static ServerResponse searchElasticsearch(String userInputText,int pageNum){
        SearchResponse response = ElasticsearchConfig.getTransportClient().prepareSearch("blog")
                .setTypes("articles")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryStringQuery(userInputText))
                .setFrom((pageNum-1)*5).setSize(5).setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        if (hits.getTotalHits()!=0){
            List<SearchElasticsearchArticleJson> searchElasticsearchArticleJsonList=new ArrayList<>();
            for (SearchHit hit : hits) {
                String json = hit.getSourceAsString();
                SearchElasticsearchArticleJson searchElasticsearchArticleJson=JsonUtil.stringToObj(json,SearchElasticsearchArticleJson.class);
                searchElasticsearchArticleJsonList.add(searchElasticsearchArticleJson);
            }
            Page page=new Page(pageNum,5,response.getHits().getTotalHits(),searchElasticsearchArticleJsonList);
            return ServerResponse.createBySuccess(page);
        }else {
            return ServerResponse.createByErrorMessage("没有你想要搜索的数据");
        }

    }

    //search API （2）
    public static ServerResponse searchElasticsearch(String userInputText){
        QueryBuilder qb = queryStringQuery(userInputText);

        SearchResponse scrollResp = ElasticsearchConfig.getTransportClient().prepareSearch("blog")
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
                .setQuery(qb)
                .setSize(100).get(); //max of 100 hits will be returned for each scroll
                //Scroll until no hits are returned
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...
            }
            scrollResp = ElasticsearchConfig.getTransportClient().prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
        } while(scrollResp.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.
        return null;
    }

}





