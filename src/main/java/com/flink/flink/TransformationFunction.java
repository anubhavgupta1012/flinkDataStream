package com.flink.flink;

import com.flink.models.Address;
import com.flink.models.Employee;
import org.apache.flink.api.common.functions.MapFunction;

public class TransformationFunction implements MapFunction<Employee, Address> {
    @Override
    public Address map(Employee employee) throws Exception {
        System.out.printf("########  %s", employee);
        return employee.getAddress();
    }
}
