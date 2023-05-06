package com.ashok.demoes.consumer.stream;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class StreamConsumer {

    @Bean
    public Consumer<KStream<?,String>> myConsumer(){
        return input ->
                input.foreach((key, value) -> {
                    log.debug("Key: {} Value: {}", key, value);
                });
    }
}
