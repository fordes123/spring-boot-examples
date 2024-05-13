package org.fordes.example.vt;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class RaceConditionTest {

    private RaceCondition raceConditionUnderTest;

    @BeforeEach
    void setUp() {
        raceConditionUnderTest = new RaceCondition();
    }

    @Test
    void testBlocking() throws InterruptedException {
        // Setup
        // Run the test
        log.info("main thread start");
        raceConditionUnderTest.blocking();
//        Thread.sleep(6000);
        log.info("main thread end");
        // Verify the results
    }
}
