package com.flink.flink.service;


import com.flink.flink.config.MongoSink;
import com.flink.flink.config.MongoSource;
import com.flink.models.Address;
import com.flink.models.Employee;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlinkInitiationService {

    private final MongoCollection<Employee> employeeCollection;
    private final MongoCollection<Address> addressCollection;
    private final MongoClient mongoClient;

    @SneakyThrows
    public void start() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("localhost", 8081);
        DataStream<Employee> sourceStream = env.addSource(new MongoSource(employeeCollection, mongoClient));

        DataStream<Address> sinkDataStream = sourceStream.map(Employee::getAddress);
        sinkDataStream.addSink(new MongoSink(mongoClient, addressCollection));

        env.execute("MongoDB Source Flink Job");
    }
}
