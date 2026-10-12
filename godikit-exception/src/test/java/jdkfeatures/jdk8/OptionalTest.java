package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDK 8 新特性测试：Optional。
 * 覆盖 of/ofNullable/empty、isPresent/ifPresent、orElse/orElseGet/orElseThrow、map/flatMap/filter。
 */
class OptionalTest {

    @Test
    void createOptional() {
        Optional<String> present = Optional.of("hello");
        assertTrue(present.isPresent());

        Optional<String> empty = Optional.empty();
        assertFalse(empty.isPresent());

        Optional<String> nullable = Optional.ofNullable(null);
        assertFalse(nullable.isPresent());

        Optional<String> nullablePresent = Optional.ofNullable("world");
        assertTrue(nullablePresent.isPresent());
    }

    @Test
    void getValue() {
        Optional<String> opt = Optional.of("hello");
        assertEquals("hello", opt.get());
    }

    @Test
    void getOnEmptyThrows() {
        Optional<String> empty = Optional.empty();
        assertThrows(NoSuchElementException.class, empty::get);
    }

    @Test
    void ifPresent() {
        StringBuilder sb = new StringBuilder();
        Optional<String> opt = Optional.of("hello");
        opt.ifPresent(sb::append);
        assertEquals("hello", sb.toString());

        Optional<String> empty = Optional.empty();
        empty.ifPresent(s -> sb.append("never"));
        assertEquals("hello", sb.toString());
    }

    @Test
    void orElseMethods() {
        Optional<String> empty = Optional.empty();
        assertEquals("default", empty.orElse("default"));
        assertEquals("from supplier", empty.orElseGet(() -> "from supplier"));

        assertThrows(IllegalStateException.class,
                () -> empty.orElseThrow(IllegalStateException::new));
    }

    @Test
    void mapAndFlatMap() {
        Optional<String> opt = Optional.of("hello");
        Optional<Integer> length = opt.map(String::length);
        assertTrue(length.isPresent());
        assertEquals(5, length.get());

        Optional<String> empty = Optional.empty();
        Optional<Integer> mapped = empty.map(String::length);
        assertFalse(mapped.isPresent());

        Optional<String> upper = opt.flatMap(s -> Optional.of(s.toUpperCase()));
        assertEquals("HELLO", upper.get());
    }

    @Test
    void filter() {
        Optional<String> opt = Optional.of("hello");
        Optional<String> filtered = opt.filter(s -> s.length() > 3);
        assertTrue(filtered.isPresent());

        Optional<String> filteredOut = opt.filter(s -> s.length() > 10);
        assertFalse(filteredOut.isPresent());
    }

    @Test
    void chainingOperations() {
        String result = Optional.of("  hello  ")
                .map(String::trim)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("H"))
                .orElse("none");
        assertEquals("HELLO", result);
    }
}
