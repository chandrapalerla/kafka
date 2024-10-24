package com.vehicledistance.controller;


import com.vehicledistance.model.VehicleSignal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleSignalController {

  private static final String INPUT_TOPIC = "vehicle-info";

  @Autowired
  private KafkaTemplate<String, VehicleSignal> kafkaTemplate;

  @PostMapping("/signals")
  public void receiveSignal(@RequestBody VehicleSignal signal) {
    kafkaTemplate.send(INPUT_TOPIC, signal.getVehicleId(), signal);
  }
}
