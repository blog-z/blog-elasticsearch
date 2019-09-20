package com.elasticsearch.SerializableElasticsearch;


import javax.print.DocFlavor;
import java.io.Serializable;

public class IndexResponseSerializable implements Serializable {

    private String id;
    private String type;
    private long version;
    private int successful;

    public IndexResponseSerializable(String id, String type, long version,int successful){
        this.id=id;
        this.type=type;
        this.version=version;
        this.successful=successful;
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

    public void setVersion(int version) {
        this.version = version;
    }

    public int getSuccessful() {
        return successful;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }
}
