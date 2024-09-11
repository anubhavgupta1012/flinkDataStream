package com.flink.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Data
@Document(collection = "employees")
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private String id;
    private String name;
    private int age;
    private String position;
    private String department;
    private double salary;
    private String hireDate;
    private String email;
    private Address address;
}
