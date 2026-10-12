package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDK 8 新特性测试：方法引用。
 * 覆盖四种形式：静态方法、实例方法、特定对象实例方法、构造方法引用。
 */
class MethodReferencesTest {

    static class Person {
        private final String name;

        Person(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        static int compareByName(Person a, Person b) {
            return a.name.compareTo(b.name);
        }
    }

    @Test
    void staticMethodReference() {
        List<Person> people = Arrays.asList(new Person("Charlie"), new Person("Alice"), new Person("Bob"));
        people.sort(Person::compareByName);
        assertEquals("Alice", people.get(0).getName());
        assertEquals("Bob", people.get(1).getName());
        assertEquals("Charlie", people.get(2).getName());
    }

    @Test
    void instanceMethodReference() {
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        List<Integer> lengths = words.stream()
                .map(String::length)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(5, 6, 6), lengths);
    }

    @Test
    void specificObjectInstanceMethodReference() {
        StringBuilder sb = new StringBuilder();
        List<String> items = Arrays.asList("a", "b", "c");
        items.forEach(sb::append);
        assertEquals("abc", sb.toString());
    }

    @Test
    void constructorReference() {
        Function<String, Person> factory = Person::new;
        Person person = factory.apply("Dave");
        assertEquals("Dave", person.getName());

        // 带参构造方法引用：Person(String name)
        Function<String, Person> withParam = Person::new;
        assertEquals("Eve", withParam.apply("Eve").getName());

        // 数组构造方法引用
        IntFunction<int[]> arrayFactory = int[]::new;
        int[] array = arrayFactory.apply(3);
        assertEquals(3, array.length);

        // char[] -> String 的构造方法引用
        Function<char[], String> fromChars = String::new;
        assertEquals("abc", fromChars.apply(new char[]{'a', 'b', 'c'}));
    }

    @Test
    void methodReferenceChaining() {
        Function<String, String> trimAndUpper = ((Function<String, String>) String::trim)
                .andThen(String::toUpperCase);
        assertEquals("HELLO", trimAndUpper.apply("  hello  "));
    }
}