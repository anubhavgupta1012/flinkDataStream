package com.flink.models;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "employees")
public class Employee {
    private String id;
    private String name;
    private int age;
    private String position;
    private String department;
    private double salary;
    private String hireDate;
    private String email;
    private Address address;


    @Data
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zip;
    }
}
