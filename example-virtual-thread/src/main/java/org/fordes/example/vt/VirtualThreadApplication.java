package org.fordes.example.vt;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@EnableAsync
@SpringBootApplication
public class VirtualThreadApplication {

    @Bean
    public TaskExecutor executors() {
        return new TaskExecutor() {
            private static final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
            @Override
            public void execute(@Nonnull Runnable task) {
                executorService.execute(task);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VirtualThreadApplication.class, args);
    }

}
