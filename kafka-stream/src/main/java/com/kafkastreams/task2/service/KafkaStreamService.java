package com.kafkastreams.task2.service;

import com.kafkastreams.task2.config.KafkaTopicTask2Config;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KafkaStreamService {

    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;
    private final KafkaTopicTask2Config kafkaTopicConfig;

    @Autowired
    public KafkaStreamService(StreamsBuilderFactoryBean streamsBuilderFactoryBean, KafkaTopicTask2Config kafkaTopicConfig) {
        this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    @Bean
    public KStream<Integer, String> processStream() throws Exception {
        StreamsBuilder builder = streamsBuilderFactoryBean.getObject();
        KStream<String, String> inputStream = builder.stream(kafkaTopicConfig.getInputTopic());

        KStream<Integer, String> processedStream = inputStream
                .filter((key, value) -> value != null)
                .flatMap((key, value) -> Arrays.stream(value.split("\\W+"))
                        .map(word -> new KeyValue<>(word.length(), word))
                        .collect(Collectors.toList()))
                .peek((key, value) -> System.out.println("Processed message: Key: " + key + ", Value: " + value));

        Map<String, KStream<Integer, String>> branchedStreams = processedStream
                .split(Named.as("words-"))
                .branch((key, value) -> value.length() < 10, Branched.as("short"))
                .branch((key, value) -> value.length() >= 10, Branched.as("long"))
                .noDefaultBranch();

        KStream<Integer, String> shortWordsStream = branchedStreams.get("words-short")
                .filter((key, value) -> value.contains("a"))
                .peek((key, value) -> System.out.println("Short word containing 'a': Key: " + key + ", Value: " + value));

        KStream<Integer, String> longWordsStream = branchedStreams.get("words-long")
                .filter((key, value) -> value.contains("a"))
                .peek((key, value) -> System.out.println("Long word containing 'a': Key: " + key + ", Value: " + value));

        KStream<Integer, String> mergedStream = shortWordsStream.merge(longWordsStream);

        mergedStream.peek((key, value) -> System.out.println("Merged stream message: Key: " + key + ", Value: " + value));

        return processedStream;
    }
}
