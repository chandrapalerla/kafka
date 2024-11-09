package com.kafkastreams.task3.service;

import com.kafkastreams.task3.config.KafkaTopicTask3Config;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class KafkaStreamService {

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;
    private final KafkaTopicTask3Config kafkaTopicConfig;

    @Autowired
    public KafkaStreamService(StreamsBuilderFactoryBean streamsBuilderFactoryBean, KafkaTopicTask3Config kafkaTopicConfig) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    @Bean
    public KStream<Long, String> kStream() throws Exception {
        StreamsBuilder builder = streamsBuilderFactoryBean.getObject();
        KStream<String, String> stream1 = builder.stream(kafkaTopicConfig.getTopic_task3_1());
        KStream<String, String> stream2 = builder.stream(kafkaTopicConfig.getTopic_task3_2());

        KStream<Long, String> filteredStream1 = stream1
                .filter((key, value) -> value != null && value.contains(":"))
                .selectKey((key, value) -> Long.parseLong(value.split(":")[0]))
                .peek((key, value) -> System.out.println("Stream1 Key: " + key + " Value: " + value));

        KStream<Long, String> filteredStream2 = stream2
                .filter((key, value) -> value != null && value.contains(":"))
                .selectKey((key, value) -> Long.parseLong(value.split(":")[0]))
                .peek((key, value) -> System.out.println("Stream2 Key: " + key + " Value: " + value));

        return filteredStream1.join(filteredStream2,
                        (value1, value2) -> value1 + "-" + value2,
                        JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMillis(30000)),
                        StreamJoined.with(Serdes.Long(), Serdes.String(), Serdes.String()))
                .peek((key, value) -> System.out.println("Joined Key: " + key + " Value: " + value));
    }
}
