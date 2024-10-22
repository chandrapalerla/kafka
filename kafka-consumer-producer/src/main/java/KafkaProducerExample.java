import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;

public class KafkaProducerExample {

    private static final String TOPIC = "first_topic";
    private static final String BROKER_LIST = "localhost:9092";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", BROKER_LIST);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());

      try (KafkaProducer<String, String> producer = new KafkaProducer<>(
          props); Scanner scanner = new Scanner(System.in)) {
        while (true) {
          System.out.println(
              "Enter vehicle signal (vehicleId, latitude, longitude) or 'exit' to quit:");
          String input = scanner.nextLine();

          if ("exit".equalsIgnoreCase(input)) {
            break;
          }

          String[] parts = input.split(",");
          if (parts.length != 3) {
            System.out.println(
                "Invalid input. Please enter in the format: vehicleId,latitude,longitude");
            continue;
          }

          String vehicleId = parts[0].trim();
          String latitude = parts[1].trim();
          String longitude = parts[2].trim();
          String message = String.format(
              "{\"vehicleId\": \"%s\", \"latitude\": %s, \"longitude\": %s}", vehicleId, latitude,
              longitude);

          ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, vehicleId, message);
          producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
              if (exception != null) {
                exception.printStackTrace();
              } else {
                System.out.printf("Produced record to topic %s partition [%d] @ offset %d%n",
                    metadata.topic(), metadata.partition(), metadata.offset());
              }
            }
          });
        }
      }
    }
}
