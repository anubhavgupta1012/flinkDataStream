package com.flink.flink.config;

import com.flink.models.Address;
import com.flink.models.Employee;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;
    @Value("${spring.data.mongodb.database}")
    private String databaseName;
    @Value("${mongodb.collection.employees}")
    private String employeeCollectionName;
    @Value("${mongodb.collection.addresses}")
    private String addressesCollectionName;

    @Bean
    public MongoCollection<Address> addressCollection(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Address> addressesCollection = database.getCollection(addressesCollectionName, Address.class);
        return addressesCollection;
    }

    @Bean
    public MongoClient getMongoClient() {
        MongoClient mongoClient = MongoClients.create(mongoUri);
        return mongoClient;
    }

    @Bean
    public MongoCollection<Employee> employeeCollection(CodecRegistry codecRegistry) {
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(mongoUri))
            .codecRegistry(codecRegistry).build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Employee> employeeCollection = database.getCollection(employeeCollectionName, Employee.class);
        return employeeCollection;
    }

    @Bean
    public CodecRegistry getCodecRegistry() {
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return codecRegistry;
    }
}