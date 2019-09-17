package com.elasticsearch.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class ElasticsearchConfig {

    private static final Logger logger= LoggerFactory.getLogger(ElasticsearchConfig.class);

    private static TransportClient transportClient;

    static {
        init();
    }

    private static void init(){
        logger.info("=========elasticsearch客户端client初始化开始============");
        Settings settings = Settings.builder()
                .put("cluster.name", "qingchun-cluster")
                .build();
        try {
            transportClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9301))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9302))
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"),9303));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        logger.info("=========elasticsearch客户端client初始化成功============");
    }

    public static TransportClient getTransportClient(){
        return transportClient;
    }

//    public static void main(String[] args) {
//        getTransportClient();
//        System.out.println(transportClient.transportAddresses().toString());
//    }
}













