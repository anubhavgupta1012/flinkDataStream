package com.flink.flink.service;


import com.flink.flink.TransformationFunction;
import com.flink.models.Address;
import com.flink.models.Employee;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertOneModel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.analysis.function.Add;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.cdc.connectors.mongodb.source.MongoDBSource;
import org.apache.flink.cdc.debezium.DebeziumDeserializationSchema;
import org.apache.flink.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.connector.mongodb.sink.MongoSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.bson.BsonDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlinkInitiationService {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;
    @Value("${spring.data.mongodb.database}")
    private String databaseName;
    @Value("${mongodb.collection.employees}")
    private String employeeCollectionName;
    @Value("${mongodb.collection.addresses}")
    private String addressesCollectionName;

    private final MongoCollection<Employee> employeeCollection;
    private final MongoCollection<Address> addressCollection;
    private final MongoClient mongoClient;

    @SneakyThrows
    public void start() {


        DebeziumDeserializationSchema deserializationSchema = new JsonDebeziumDeserializationSchema();
        MongoDBSource source = MongoDBSource.<Employee>builder()
            .hosts("localhost:27017")
            .databaseList("EmployeeDB")
            .collectionList("employees")
            .deserializer(deserializationSchema).build();

        MongoSink sink = MongoSink.<Address>builder().setUri("mongodb://localhost:27017/EmployeeDB")
            .setDatabase("EmployeeDB")
            .setCollection("addresses")
            .setSerializationSchema((element, sinkContext) -> {
                log.info("######## {}", element);
                return new InsertOneModel<>(BsonDocument.parse(element.toString()));
            })
            .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.fromSource(source, WatermarkStrategy.noWatermarks(), "From Mongo")
            .map(new TransformationFunction())
            .returns(Address.class)
            .map(element -> {
                log.info("######## {}", element);
                return element;
            })
            .sinkTo(sink);

        env.execute("MongoDB Source Flink Job to Mongo Sink");
        log.info("MongoDB Source Flink Job started");
    }
}
