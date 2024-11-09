package com.kafkastreams.task4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafkastreams.task4.model.Employee;
import com.kafkastreams.task4.service.EmployeeDeserializer;
import com.kafkastreams.task4.service.EmployeeSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeSerDeTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmployeeSerializer serializer = new EmployeeSerializer();
    private final EmployeeDeserializer deserializer = new EmployeeDeserializer();

    @Test
    public void testSerialization() throws Exception {
        Employee employee = new Employee();
        employee.setName("John");
        employee.setCompany("EPAM");
        employee.setPosition("developer");
        employee.setExperience(5);

        byte[] bytes = serializer.serialize("test-topic", employee);
        assertNotNull(bytes);

        Employee deserializedEmployee = deserializer.deserialize("test-topic", bytes);
        assertNotNull(deserializedEmployee);
        assertEquals(employee.getName(), deserializedEmployee.getName());
        assertEquals(employee.getCompany(), deserializedEmployee.getCompany());
        assertEquals(employee.getPosition(), deserializedEmployee.getPosition());
        assertEquals(employee.getExperience(), deserializedEmployee.getExperience());
    }
}
