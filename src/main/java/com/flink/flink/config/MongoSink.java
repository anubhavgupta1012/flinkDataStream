package com.flink.flink.config;


import com.flink.models.Address;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

@RequiredArgsConstructor
public class MongoSink extends RichSinkFunction<Address> {
    private final MongoClient mongoClient;
    private final MongoCollection<Address> addressCollection;

    @Override
    public void invoke(Address value, Context context) {
        addressCollection.insertOne(value);
    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }
}