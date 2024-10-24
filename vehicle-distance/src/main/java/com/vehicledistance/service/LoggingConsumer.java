package com.vehicledistance.service;

import com.vehicledistance.model.VehicleDistance;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class LoggingConsumer {

  @KafkaListener(topics = "vehicle-distance", groupId = "logging-group")
  public void listen(ConsumerRecord<String, VehicleDistance> record) {
    VehicleDistance distance = record.value();
    System.out.printf("Vehicle ID: %s, Total Distance: %.2f km%n", distance.getVehicleId(),
        distance.getTotalDistance());
  }
}