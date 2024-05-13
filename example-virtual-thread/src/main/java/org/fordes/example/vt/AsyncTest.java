package org.fordes.example.vt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author fordes on 2024/4/30
 */
@Slf4j
@Component
public class AsyncTest {

    @Async
    public void asyncFunc() throws InterruptedException {
        Thread.sleep(5000);
        log.info("async func finished");
    }

}