package com.flink.flink.config;

import com.flink.models.Employee;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;


@org.springframework.context.annotation.Configuration
@RequiredArgsConstructor
public class MongoSource extends RichSourceFunction<Employee> {
    private final MongoCollection<Employee> collection;
    private final MongoClient mongoClient;


    @Override
    public void run(SourceContext<Employee> ctx) throws Exception {
        for (Employee employee : collection.find()) {
            ctx.collect(employee);
        }
    }

    @Override
    public void cancel() {
        // Cleanup resources
        mongoClient.close();
    }
}