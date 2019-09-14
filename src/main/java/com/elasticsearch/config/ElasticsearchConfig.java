package com.elasticsearch.config;

import org.apache.http.HttpHost;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticsearchConfig {

    private static final Logger logger= LoggerFactory.getLogger(ElasticsearchConfig.class);

    private static RestClient lowRestClient=null;

    static {
        init();
    }

    private static void init(){
        RestClientBuilder restClientBuilder= RestClient.builder(new HttpHost("localhost",9201,"http"));
        restClientBuilder
                .setRequestConfigCallback(builder -> {
                    builder.setConnectTimeout(1000)
                            .setSocketTimeout(2000)
                            .setConnectionRequestTimeout(3000);
                    return builder;
                })
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultIOReactorConfig(IOReactorConfig.custom()
                        .setIoThreadCount(100)
                        .setConnectTimeout(10000)
                        .setSoTimeout(10000)
                        .build()
                ))
                .setMaxRetryTimeoutMillis(10000)
        ;
        lowRestClient=restClientBuilder.build();
        logger.info("=========elasticsearch初始化成功============");
    }

    public static RestClient getLowRestClient(){
        return lowRestClient;
    }
}
