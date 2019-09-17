package com.elasticsearch.SerializableElasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.index.get.GetResult;

import java.io.Serializable;

public class GetResponseSerializable extends GetResponse implements Serializable {

    public GetResponseSerializable(GetResult getResult) {
        super(getResult);
    }
}
