package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JDK 8 新特性测试：StringJoiner。
 * 覆盖分隔符、前缀后缀、空值处理、merge 操作。
 */
class StringJoinerTest {

    @Test
    void basicJoining() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add("Apple").add("Banana").add("Cherry");
        assertEquals("Apple, Banana, Cherry", joiner.toString());
    }

    @Test
    void withPrefixAndSuffix() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        joiner.add("Apple").add("Banana");
        assertEquals("[Apple, Banana]", joiner.toString());
    }

    @Test
    void emptyJoinerWithPrefixSuffix() {
        // StringJoiner(delimiter, prefix, suffix) 的 emptyValue 默认为 prefix + suffix
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        assertEquals("[]", joiner.toString());
    }

    @Test
    void setEmptyValue() {
        StringJoiner joiner = new StringJoiner(", ").setEmptyValue("EMPTY");
        assertEquals("EMPTY", joiner.toString());

        joiner.add("item");
        assertEquals("item", joiner.toString());
    }

    @Test
    void mergeJoiners() {
        StringJoiner j1 = new StringJoiner(", ").add("A").add("B");
        StringJoiner j2 = new StringJoiner("-").add("C").add("D");
        j1.merge(j2);
        assertEquals("A, B, C-D", j1.toString());
    }
}