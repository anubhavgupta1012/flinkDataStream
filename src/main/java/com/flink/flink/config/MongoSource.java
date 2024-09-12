package com.flink.flink.config;

import com.flink.models.Employee;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.springframework.context.annotation.Configuration;


@NoArgsConstructor
@AllArgsConstructor
public class MongoSource extends RichSourceFunction<Employee> {
    private transient MongoCollection<Employee> collection;
    private transient MongoClient mongoClient;


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