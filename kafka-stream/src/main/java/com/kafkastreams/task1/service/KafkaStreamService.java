package com.kafkastreams.task1.service;

import com.kafkastreams.task1.config.KafkaTopicTask1Config;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class KafkaStreamService {

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;
    private final KafkaTopicTask1Config kafkaTopicConfig;

    @Autowired
    public KafkaStreamService(StreamsBuilderFactoryBean streamsBuilderFactoryBean, KafkaTopicTask1Config kafkaTopicConfig) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    @Bean
    public KStream<String, String> processStream() throws Exception {
        StreamsBuilder builder = streamsBuilderFactoryBean.getObject();
        KStream<String, String> inputStream = null;
        if (builder != null) {
            inputStream = builder.stream(kafkaTopicConfig.getInputTopic());
        }

        if (inputStream != null) {
            inputStream.foreach((key, value) -> {
                System.out.println("Read from " + kafkaTopicConfig.getInputTopic() + ": Key: " + key + ", Value: " + value);
            });
        }
        if (inputStream != null) {
            inputStream.to(kafkaTopicConfig.getOutputTopic());
        }
        return inputStream;
    }
}
