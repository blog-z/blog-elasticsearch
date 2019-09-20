package com.elasticsearch.SerializableElasticsearch;

import org.elasticsearch.action.support.replication.ReplicationResponse;

import java.io.Serializable;

public class ElasticsearchShardInfo implements Serializable {
    private long total;
    private int successful;
    private ReplicationResponse.ShardInfo.Failure[] failures;

    public ElasticsearchShardInfo(long total, int successful, ReplicationResponse.ShardInfo.Failure[] failures) {
        this.total = total;
        this.successful = successful;
        this.failures = failures;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getSuccessful() {
        return successful;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }

    public ReplicationResponse.ShardInfo.Failure[] getFailures() {
        return failures;
    }

    public void setFailures(ReplicationResponse.ShardInfo.Failure[] failures) {
        this.failures = failures;
    }
}
