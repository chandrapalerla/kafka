package com.vehicledistance.service;

import com.vehicledistance.model.VehicleDistance;
import com.vehicledistance.model.VehicleSignal;
import com.vehicledistance.model.VehicleState;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculatorConsumer {

  private static final String INPUT_TOPIC = "vehicle-info";
  private static final String OUTPUT_TOPIC = "vehicle-distance";

  private final Map<String, VehicleState> vehicleStateMap = new ConcurrentHashMap<>();

  @Autowired
  private KafkaTemplate<String, VehicleDistance> kafkaTemplate;

  @KafkaListener(topics = INPUT_TOPIC, groupId = "distance-calculator-group")
  public void listen(ConsumerRecord<String, VehicleSignal> record) {
    VehicleSignal signal = record.value();
    String vehicleId = signal.getVehicleId();

    vehicleStateMap.compute(vehicleId, (id, state) -> {
      if (state == null) {
        state = new VehicleState(signal.getLatitude(), signal.getLongitude());
      } else {
        double distance = calculateDistance(state.getLatitude(), state.getLongitude(),
            signal.getLatitude(), signal.getLongitude());
        state.addDistance(distance);
        state.updatePosition(signal.getLatitude(), signal.getLongitude());
      }
      return state;
    });

    VehicleDistance vehicleDistance = new VehicleDistance(vehicleId,
        vehicleStateMap.get(vehicleId).getTotalDistance());
    kafkaTemplate.send(OUTPUT_TOPIC, vehicleDistance.getVehicleId(), vehicleDistance);
  }

  private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    // Implement the Haversine formula to calculate distance between two points.
    final int R = 6371; // Radius of the Earth in km
    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // Distance in km
  }
}
