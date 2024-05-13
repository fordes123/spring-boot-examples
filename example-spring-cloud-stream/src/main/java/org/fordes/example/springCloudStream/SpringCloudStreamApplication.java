package org.fordes.example.springCloudStream;

import lombok.extern.slf4j.Slf4j;
import org.fordes.example.jacksonUtil.JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@SpringBootApplication
public class SpringCloudStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamApplication.class, args);
    }

    public record Data(String data, LocalDateTime time) {}

    @Bean
    public Supplier<Data> producer() {
        return () -> {
            Data data = new Data(UUID.randomUUID().toString(), LocalDateTime.now());
            log.info("kafka-producer: {}", JSON.toStr(data));
            return data;
        };
    }

    @Bean
    public Function<Data, Data> exchange() {
        return data -> new Data(data.data().toUpperCase(), data.time());
    }

    @Bean
    public Consumer<Data> consumer() {
        return data -> log.info("rabbit-consumer: {}", JSON.toStr(data));
    }
}