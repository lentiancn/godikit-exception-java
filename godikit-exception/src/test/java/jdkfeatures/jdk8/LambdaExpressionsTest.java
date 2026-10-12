package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDK 8 新特性测试：Lambda 表达式。
 * 验证无参、单参、多参、代码块形式的 Lambda，以及捕获外部变量的行为。
 */
class LambdaExpressionsTest {

    @FunctionalInterface
    interface Greeter {
        String greet(String name);
    }

    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
    }

    @FunctionalInterface
    interface Action {
        void run();
    }

    @Test
    void singleParameterLambda() {
        Greeter greeter = name -> "Hello, " + name;
        assertEquals("Hello, Len", greeter.greet("Len"));
    }

    @Test
    void multipleParametersLambda() {
        Calculator add = (a, b) -> a + b;
        assertEquals(5, add.calculate(2, 3));

        Calculator multiply = (int a, int b) -> a * b;
        assertEquals(6, multiply.calculate(2, 3));
    }

    @Test
    void noParameterLambda() {
        Action action = () -> {
        };
        action.run();
        assertTrue(true);
    }

    @Test
    void lambdaWithStatementBlock() {
        Calculator complex = (a, b) -> {
            int sum = a + b;
            return sum * 2;
        };
        assertEquals(10, complex.calculate(2, 3));
    }

    @Test
    void lambdaCapturesEffectivelyFinalVariable() {
        int factor = 10;
        Calculator multiplier = (a, b) -> (a + b) * factor;
        assertEquals(50, multiplier.calculate(2, 3));
    }

    @Test
    void lambdaAsComparator() {
        Comparator<String> byLength = (s1, s2) -> s1.length() - s2.length();
        assertTrue(byLength.compare("apple", "kiwi") > 0);
        assertTrue(byLength.compare("fig", "banana") < 0);
    }

    @Test
    void lambdaMutatesAtomicVariable() {
        AtomicInteger counter = new AtomicInteger();
        Action increment = counter::incrementAndGet;
        increment.run();
        increment.run();
        assertEquals(2, counter.get());
    }
}
