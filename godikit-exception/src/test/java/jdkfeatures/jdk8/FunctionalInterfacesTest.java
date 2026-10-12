package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JDK 8 新特性测试：函数式接口与 java.util.function 包内建接口。
 * 覆盖 Predicate、Function、Consumer、Supplier，以及自定义函数式接口。
 */
class FunctionalInterfacesTest {

    @Test
    void predicateTests() {
        Predicate<String> isEmpty = String::isEmpty;
        assertTrue(isEmpty.test(""));
        assertFalse(isEmpty.test("hello"));

        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isPositive = n -> n > 0;
        assertTrue(isEven.and(isPositive).test(4));
        assertFalse(isEven.and(isPositive).test(-2));
        assertFalse(isEven.or(isPositive).test(-3));
        assertTrue(isEven.or(isPositive).test(-2));
        assertTrue(isEven.or(isPositive).test(3));
        assertTrue(isEven.negate().test(3));
    }

    @Test
    void functionTests() {
        Function<String, Integer> length = String::length;
        assertEquals(5, length.apply("hello"));

        Function<Integer, Integer> doubleIt = x -> x * 2;
        Function<Integer, Integer> addOne = x -> x + 1;
        // andThen: 先乘 2，再加 1，即 2 * 2 + 1 = 5
        assertEquals(5, doubleIt.andThen(addOne).apply(2));
        // compose: 先加 1，再乘 2，即 (2 + 1) * 2 = 6
        assertEquals(6, doubleIt.compose(addOne).apply(2));
    }

    @Test
    void consumerTests() {
        StringBuilder sb = new StringBuilder();
        Consumer<String> append = s -> sb.append(s).append(",");
        append.accept("a");
        append.accept("b");
        assertEquals("a,b,", sb.toString());

        Consumer<String> upper = s -> sb.append(s.toUpperCase());
        append.andThen(upper).accept("c");
        assertTrue(sb.toString().endsWith("c,C"));
    }

    @Test
    void supplierTests() {
        Supplier<String> greeting = () -> "Hello World";
        assertEquals("Hello World", greeting.get());

        Supplier<Double> random = Math::random;
        double value = random.get();
        assertTrue(value >= 0.0 && value < 1.0);
    }

    @Test
    void customTriFunction() {
        TriFunction<Integer, Integer, Integer, Integer> sum = (a, b, c) -> a + b + c;
        assertEquals(6, sum.apply(1, 2, 3));
    }

    @FunctionalInterface
    interface TriFunction<A, B, C, R> {
        R apply(A a, B b, C c);
    }
}
