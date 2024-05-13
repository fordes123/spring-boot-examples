package org.fordes.example.vt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author fordes on 2024/4/16
 */
@Slf4j
public class CreateVirtualThread {

    public void createAndStart() {
        Thread vt = Thread.startVirtualThread(() -> {
            Thread.currentThread().setName("virtual thread");
            log.info("create and start virtual thread");
        });
    }

    public void createManualStart() {
        Thread vt = Thread.ofVirtual().unstarted(() -> {
            Thread.currentThread().setName("virtual thread");
            log.info("create and manual start virtual thread");
        });
        vt.start();
    }

    public void createByFactory() {
        ThreadFactory factory = Thread.ofVirtual().factory();
        Thread vt = factory.newThread(() -> {
            Thread.currentThread().setName("virtual thread");
            log.info("create virtual thread by factory");
        });
        vt.start();
    }

    public void createByExecutor() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.execute(() -> {
                Thread.currentThread().setName("virtual thread");
                log.info("create virtual thread by executor");
            });
        }
    }
}