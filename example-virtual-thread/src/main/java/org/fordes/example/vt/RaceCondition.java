package org.fordes.example.vt;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author fordes on 2024/4/16
 */
@Slf4j
public class RaceCondition {

    public void blocking() throws InterruptedException {

        final Path path = Path.of("temp");

        Thread.startVirtualThread(() -> {
            Thread.currentThread().setName("virtual thread-1");
            try (BufferedWriter writer = Files.newBufferedWriter(path,
                    StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

                writer.write("hello world");
                writer.flush();
                log.info("write success");

                Thread.sleep(3000);
            } catch (Exception ignored) {
            }
        });

        Thread.sleep(500);
        Thread.startVirtualThread(() -> {
            Thread.currentThread().setName("virtual thread-2");

            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                char[] chars = new char[1024];
                while (reader.read(chars) != -1) {
                    log.info("read: {}", new String(chars));
                }
            } catch (Exception ignored) {
            }
        });
    }
}