package org.fordes.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class JSONTest {

    @Test
    void testCanToJson() {
        assertThat(JSON.canToJson("obj")).isFalse();
        assertThat(JSON.canToJson(Map.of("key", "value"))).isTrue();
    }

    @Test
    void testIsJson() {
        assertThat(JSON.isJson("json")).isFalse();
        assertThat(JSON.isJson("{}xxxxx")).isFalse();
        assertThat(JSON.isJson("[]")).isTrue();
    }

    @Test
    void testToJsonStr() {
        assertThat(JSON.toJsonStr(Map.of("key", "value"))).isEqualTo("{\"key\":\"value\"}");
        assertThat(JSON.toJsonStr("obj")).isNull();
        assertThat(JSON.toJsonStr(3L)).isNull();
    }

    @Test
    void testToStr() {
        assertThat(JSON.toStr(LocalDateTime.of(2023, 1, 1, 0, 0))).isEqualTo("2023-01-01 00:00:00");
        assertThat(JSON.toStr("obj")).isEqualTo("obj");
        assertThat(JSON.toStr(Map.of("key", 3))).isEqualTo("{\"key\":3}");
    }

    @Test
    void testToJsonByte() {
        assertThat(JSON.toJsonByte(Map.of("key", 3))).isEqualTo("{\"key\":3}".getBytes());
    }

    @Test
    void testToList() {
        assertThat(JSON.toList("[\"value\"]", String.class)).isEqualTo(List.of("value"));
        assertThat(JSON.toList("json", String.class)).isNull();
        assertThat(JSON.toList("[]", String.class)).isEqualTo(Collections.emptyList());
    }

    @Test
    void testToSet() {
        assertThat(JSON.toSet("[1,1,5,9,9]", Integer.class)).isEqualTo(Set.of(1,5,9));
        assertThat(JSON.toSet("[]", String.class)).isEqualTo(Collections.emptySet());
    }

    @Test
    void testToBean1() {
        assertThat(JSON.toBean("{\"1\":\"1\", \"2\":2}", new TypeReference<Map<Integer, String>>() {})).isEqualTo(Map.of(1, "1", 2, "2"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testToBean2() {
        assertThat(JSON.toBean("{\"1\":\"1\", \"2\":2}", Map.class)).isEqualTo(Map.of("1", "1", "2", 2));
    }

    @Test
    void testGetNode() {
        final JsonNode result = JSON.getNode("{\"key\":\"value\"}", "key");
        assertThat(result).isNotNull();
    }

    @Test
    void testGet1() {
        assertThat(JSON.get("{\"key\":[false]}", "key", new TypeReference<List<Boolean>>() {})).isEqualTo(List.of(false));
    }

    @Test
    void testGet2() {
        assertThat(JSON.get("{\"key\":\"value\"}", "key", String.class)).isEqualTo("value");
    }

    @Test
    void testGetList() {
        assertThat(JSON.getList("{\"key\":[\"value\"]}", "key", String.class)).isEqualTo(List.of("value"));
        assertThat(JSON.getList("{\"key\":[]}", "key", String.class)).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetSet() {
        assertThat(JSON.getSet("{\"key\":[\"value\",\"value\"]}", "key", String.class)).isEqualTo(new HashSet<>(){{
            add("value");
        }});
        assertThat(JSON.getSet("{\"key\":[]}", "key", String.class)).isEqualTo(Set.of());
    }

    @Test
    void testGetStr() {
        assertThat(JSON.getStr("{\"key\":1}", "key")).isEqualTo("1");
        assertThat(JSON.getStr("json", "key")).isNull();
    }

    @Test
    void testGetInt() {
        assertThat(JSON.getInt("{\"key\":0}", "key")).isEqualTo(0);
        assertThat(JSON.getInt("{\"key\":3.14}",  "key")).isEqualTo(3);
    }

    @Test
    void testGetBool() {
        assertThat(JSON.getBool("{\"key\":false}", "key")).isFalse();
        assertThat(JSON.getBool("{\"key\":1}", "key")).isTrue();
        assertThat(JSON.getBool("{\"key\":\"false\"}", "key")).isFalse();
        assertThat(JSON.getBool("{\"key\":\"666\"}", "key")).isFalse();
    }

    @Test
    void testGetBoolAbs() {
        assertThat(JSON.getBoolAbs("{\"key\":true}", "key")).isTrue();
        assertThat(JSON.getBoolAbs("{\"key\":1}", "key")).isNull();
        assertThat(JSON.getBoolAbs("{\"key\":\"true\"}", "key")).isNull();
        assertThat(JSON.getBoolAbs("{\"key\":\"666\"}", "key")).isNull();
    }

    @Test
    void testGetDecimal() {
        assertThat(JSON.getDecimal("{\"key\":3.1415926535897932384626433832795}", "key")).isEqualTo(BigDecimal.valueOf(3.1415926535897932384626433832795d));
        assertThat(JSON.getDecimal("json", "key")).isNull();
    }

    @Test
    void testGetLong() {
        assertThat(JSON.getLong("{\"key\":14159265358979}", "key")).isEqualTo(14159265358979L);
    }

    @Test
    void testGetDouble() {
        assertThat(JSON.getDouble("{\"key\":3.1415926535897932384626433832795}", "key")).isEqualTo(3.1415926535897932384626433832795d);
    }

    @Test
    void testConvert1() {
        record Origin(String value) {}
        record Target(String value) {}
        assertThat(JSON.convert(new Origin("1"), Target.class)).isEqualTo(new Target("1"));
    }

    @Test
    void testConvert2() {
        record Origin(List<String> value) {}
        record Target<T>(List<T> value) {}
        assertThat(JSON.convert(new Origin(List.of("1")), new TypeReference<Target<String>>() {
        })).isEqualTo(new Target<>(List.of("1")));
    }
}
