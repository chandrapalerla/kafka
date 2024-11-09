package com.kafkastreams.task3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicTask3Config {
    @Value("${kafka.topic.task3.1}")
    private String topic_task3_1;

    @Value("${kafka.topic.task3.2}")
    private String topic_task3_2;

    public String getTopic_task3_1() {
        return topic_task3_1;
    }

    public void setTopic_task3_1(String topic_task3_1) {
        this.topic_task3_1 = topic_task3_1;
    }

    public String getTopic_task3_2() {
        return topic_task3_2;
    }

    public void setTopic_task3_2(String topic_task3_2) {
        this.topic_task3_2 = topic_task3_2;
    }
}
