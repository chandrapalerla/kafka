package com.kafkastreams.task4.service;

import com.kafkastreams.task4.model.Employee;
import org.apache.kafka.common.serialization.Serdes;

public class EmployeeSerde extends Serdes.WrapperSerde<Employee> {
    public EmployeeSerde() {
        super(new EmployeeSerializer(), new EmployeeDeserializer());
    }
}
