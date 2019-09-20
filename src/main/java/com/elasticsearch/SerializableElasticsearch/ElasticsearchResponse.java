package com.elasticsearch.SerializableElasticsearch;

import java.io.Serializable;

public class ElasticsearchResponse<T> implements Serializable {

    private String id;
    private String type;
    private long version;
    private T result;
    private ElasticsearchShardInfo shardInfo;

    public ElasticsearchResponse(String id, String type, long version, T result, ElasticsearchShardInfo shardInfo) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.result = result;
        this.shardInfo = shardInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ElasticsearchShardInfo getShardInfo() {
        return shardInfo;
    }

    public void setShardInfo(ElasticsearchShardInfo shardInfo) {
        this.shardInfo = shardInfo;
    }

}
