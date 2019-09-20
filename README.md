# blog-elasticsearch

#### 介绍
此项目是基于dubbo-2.7的提供搜索服务

#### 软件架构
elasticsearch-5.6.16
在一台机器上模拟集群




```
node.name: node-1
cluster.name: qingchun-cluster
http.port: 9201
transport.tcp.port: 9301
discovery.zen.ping.unicast.hosts: ["127.0.0.1:9301", "127.0.0.1:9302","127.0.0.1:9303"]
discovery.zen.minimum_master_nodes: 2
http.cors.enabled: true
http.cors.allow-origin: "*"

node.name: node-2
cluster.name: qingchun-cluster
http.port: 9202
transport.tcp.port: 9302
discovery.zen.ping.unicast.hosts: ["127.0.0.1:9301", "127.0.0.1:9302","127.0.0.1:9303"]
discovery.zen.minimum_master_nodes: 2
http.cors.enabled: true
http.cors.allow-origin: "*"

node.name: node-3
cluster.name: qingchun-cluster
http.port: 9203
transport.tcp.port: 9303
discovery.zen.ping.unicast.hosts: ["127.0.0.1:9301", "127.0.0.1:9302","127.0.0.1:9303"]
discovery.zen.minimum_master_nodes: 2
http.cors.enabled: true
http.cors.allow-origin: "*"
```





#### 安装教程

安装中文分词器

```
 ./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v5.6.16/elasticsearch-analysis-ik-5.6.16.zip

使用es-head

http://localhost:9201/blog/articles
{
  "settings": {
    "analysis": {
      "analyzer": {
        "default": {
          "type": "ik_max_word",
          "tokenizer": "ik_max_word"
        }
      }
    }
  },
  "mappings": {
    "articles": {
      "properties": {
        "article_id": {
          "type": "string"
        },
        "article_user_id": {
          "type": "string"
        },
        "article_title": {
          "type": "string"
        },
        "article_content": {
          "type": "text"
        },
        "create_time": {
          "format": "yyyy-MM-dd HH:mm:ss",
          "type": "date"
        },
        "update_time": {
          "format": "yyyy-MM-dd HH:mm:ss",
          "type": "date"
        }
      }
    }
  }
}
```


#### 使用说明


