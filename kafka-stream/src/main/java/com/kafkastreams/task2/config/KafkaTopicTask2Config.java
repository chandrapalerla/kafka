package com.kafkastreams.task2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicTask2Config {

    @Value("${kafka.topic.task2.input}")
    private String inputTopic;

    @Value("${kafka.topic.task2.short}")
    private String shortWordsTopic;

    @Value("${kafka.topic.task2.long}")
    private String longWordsTopic;

    public String getInputTopic() {
        return inputTopic;
    }

    public String getShortWordsTopic() {
        return shortWordsTopic;
    }

    public String getLongWordsTopic() {
        return longWordsTopic;
    }
}
