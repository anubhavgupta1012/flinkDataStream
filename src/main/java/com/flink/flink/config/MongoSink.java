package com.flink.flink.config;


import com.flink.models.Address;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

@NoArgsConstructor
@AllArgsConstructor
public class MongoSink extends RichSinkFunction<Address> {
    private transient MongoClient mongoClient;
    private transient MongoCollection<Address> addressCollection;

    @Override
    public void invoke(Address value, Context context) {
        addressCollection.insertOne(value);
    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }
}