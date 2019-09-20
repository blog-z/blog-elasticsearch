package com.elasticsearch.SerializableElasticsearch;

import java.io.Serializable;

public class ElasticsearchResult implements Serializable {
    private byte op;
    private String lowercase;
    private String name;
    private int ordinal;

    public ElasticsearchResult(byte op, String lowercase, String name, int ordinal) {
        this.op = op;
        this.lowercase = lowercase;
        this.name = name;
        this.ordinal = ordinal;
    }

    public long getOp() {
        return op;
    }

    public void setOp(byte op) {
        this.op = op;
    }

    public String getLowercase() {
        return lowercase;
    }

    public void setLowercase(String lowercase) {
        this.lowercase = lowercase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
