package org.fordes.example.vt;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Resource
    private AsyncTest asyncTest;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        log.info("start call");
        asyncTest.asyncFunc();
        log.info("end call");
        return "async func called";
    }
}
