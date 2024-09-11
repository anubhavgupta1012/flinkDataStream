package com.flink.repos;

import com.flink.models.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, Integer> {

    Optional<Employee> findByName(String name);
}
