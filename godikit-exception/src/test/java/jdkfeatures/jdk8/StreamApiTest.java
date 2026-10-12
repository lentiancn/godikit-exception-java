package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDK 8 新特性测试：Stream API。
 * 覆盖创建、中间操作（filter/map/sorted/distinct/limit/skip）、终端操作（collect/reduce/forEach/count）。
 */
class StreamApiTest {

    @Test
    void streamFromCollection() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> upper = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList("ALICE", "BOB", "CHARLIE"), upper);
    }

    @Test
    void streamFilter() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> evens = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(2, 4, 6), evens);
    }

    @Test
    void streamDistinctAndSorted() {
        List<Integer> numbers = Arrays.asList(3, 1, 2, 3, 2, 1);
        List<Integer> distinctSorted = numbers.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(1, 2, 3), distinctSorted);
    }

    @Test
    void streamLimitAndSkip() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> result = numbers.stream()
                .skip(2)
                .limit(3)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(3, 4, 5), result);
    }

    @Test
    void streamReduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream().reduce(0, Integer::sum);
        assertEquals(15, sum);

        Optional<Integer> product = numbers.stream().reduce((a, b) -> a * b);
        assertTrue(product.isPresent());
        assertEquals(120, product.get());
    }

    @Test
    void streamMatchOperations() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        assertTrue(numbers.stream().allMatch(n -> n > 0));
        assertTrue(numbers.stream().anyMatch(n -> n > 4));
        assertFalse(numbers.stream().noneMatch(n -> n == 3));
    }

    @Test
    void streamFindFirstAndFindAny() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        Optional<String> first = names.stream().filter(n -> n.startsWith("B")).findFirst();
        assertTrue(first.isPresent());
        assertEquals("Bob", first.get());
    }

    @Test
    void primitiveStream() {
        int sum = IntStream.range(1, 6).sum();
        assertEquals(15, sum);

        int[] squares = IntStream.rangeClosed(1, 3).map(n -> n * n).toArray();
        assertArrayEquals(new int[]{1, 4, 9}, squares);
    }

    @Test
    void streamFlatMap() {
        List<List<Integer>> nested = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5)
        );
        List<Integer> flat = nested.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), flat);
    }

    @Test
    void streamGenerateAndIterate() {
        List<Integer> firstFive = Stream.iterate(0, n -> n + 2)
                .limit(5)
                .collect(Collectors.toList());
        assertEquals(Arrays.asList(0, 2, 4, 6, 8), firstFive);
    }
}