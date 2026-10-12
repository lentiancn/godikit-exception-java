package jdkfeatures.jdk21;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatternMatchingSwitchTest {

    sealed interface Result permits Success, Failure {
    }

    record Success(String message) implements Result {
    }

    record Failure(String error) implements Result {
    }

    @Test
    void switchSupportsTypePatterns() {
        Object value = 100;

        String result = switch (value) {
            case Integer i -> "integer:" + i;
            case String s -> "string:" + s;
            default -> "unknown";
        };

        assertEquals("integer:100", result);
    }

    @Test
    void switchSupportsSealedHierarchy() {
        Result result = new Success("ok");

        String message = switch (result) {
            case Success s -> s.message();
            case Failure f -> f.error();
        };

        assertEquals("ok", message);
    }

    record User(String name, int age) {
    }

    @Test
    void switchCanUseRecordPattern() {
        Object value = new User("Bob", 18);

        String message = switch (value) {
            case User(String name, int age) -> name + ":" + age;
            default -> "none";
        };

        assertEquals("Bob:18", message);
    }
}
