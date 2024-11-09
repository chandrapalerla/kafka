package com.kafkastreams.task4.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkastreams.task4.model.Employee;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class EmployeeSerializer implements Serializer<Employee> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Employee data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing Employee", e);
        }
    }

    @Override
    public void close() {
    }
}

