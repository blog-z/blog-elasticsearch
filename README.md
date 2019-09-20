# blog-elasticsearch

#### 介绍
此项目是基于dubbo-2.7的提供搜索服务

#### 软件架构
elasticsearch-5.6.16
在一台机器上模拟集群

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




#### 安装教程

安装中文分词器

#### 使用说明


