package com.ashok.demoes.producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.time.Duration;
import java.util.Date;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public MessageChannel controlChannel() {
        return MessageChannels.direct().get();
    }
    private KafkaProperties kafkaProperties;
    private String kafkaTopic;

    public KafkaProducerConfig(KafkaProperties kafkaProperties, @Value("${app.topic-name}") String kafkaTopic){
        this.kafkaProperties = kafkaProperties;
        this.kafkaTopic = kafkaTopic;
    }

    @Bean
    public IntegrationFlow producerIntegrationFlow(){

        return IntegrationFlow.from(() -> new GenericMessage<>(""),
                        c -> c.poller(Pollers.fixedRate(Duration.ofSeconds(5)))
                                .id("kafkaProducerBean"))
                .transform(message -> new Date().toString())
                .log()
                .channel("to-kafka-producer-template")
                .get();
    }

    @Bean
    public IntegrationFlow kafkaProducerTemplate(KafkaTemplate<?,?> kafkaTemplate){
        kafkaTemplate.setDefaultTopic(this.kafkaTopic);
        return IntegrationFlow.from("to-kafka-producer-template")
                .handle(Kafka.outboundChannelAdapter(kafkaTemplate))
                .get();
    }

}
