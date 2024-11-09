package com.kafkastreams.task4.service;

import com.kafkastreams.task4.model.Employee;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

public class KafkaStreamService {
    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @Autowired
    public KafkaStreamService(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
    }

    @Bean
    public KStream<String, Employee> kStream() throws Exception {
        StreamsBuilder builder = streamsBuilderFactoryBean.getObject();
        KStream<String, Employee> stream = builder.stream("task4");

        stream.filter((key, value) -> value != null)
                .foreach((key, value) -> System.out.println("Key: " + key + " Value: " + value));
        return stream;
    }
}
