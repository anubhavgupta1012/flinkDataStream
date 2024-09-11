package com.flink.service;

import com.flink.models.Employee;
import com.flink.repos.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee getAllEmployee(String name) {
        Optional<Employee> byName = employeeRepository.findByName(name);
        return byName.get();
    }
}
