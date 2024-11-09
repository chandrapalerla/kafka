package com.kafkastreams.task1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicTask1Config {

    @Value("${kafka.topic.task1.input}")
    private String inputTopic;

    @Value("${kafka.topic.task1.input}")
    private String outputTopic;

    public String getInputTopic() {
        return inputTopic;
    }

    public String getOutputTopic() {
        return outputTopic;
    }
}
