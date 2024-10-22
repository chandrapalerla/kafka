import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumerExample {

  private static final String INPUT_TOPIC = "first_topic";
  private static final String BROKER_LIST = "localhost:9092";
  private static final String GROUP_ID = " my-first-application";

  public static void main(String[] args) {
    Properties consumerProps = new Properties();
    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());

    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);
    consumer.subscribe(Collections.singletonList(INPUT_TOPIC));

    ConsumerRecords<String, String> records = consumer.poll(1000);
    for (ConsumerRecord<String, String> record : records) {
      String vehicleId = record.key();
      String message = record.value();
      System.out.println("vehicleId::::" + vehicleId + "message::::::::::" + message);
    }
  }
}
