package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDK 8 新特性测试：Arrays.parallelSort 与并行流。
 * 验证并行排序的正确性，以及 parallelStream 的行为。
 */
class ParallelSortTest {

    @Test
    void parallelSortIntArray() {
        int[] array = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        Arrays.parallelSort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, array);
    }

    @Test
    void parallelSortStringArray() {
        String[] array = {"banana", "apple", "cherry", "date"};
        Arrays.parallelSort(array);
        assertArrayEquals(new String[]{"apple", "banana", "cherry", "date"}, array);
    }

    @Test
    void parallelSortLargeArray() {
        int[] array = new int[100_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = array.length - i;
        }
        Arrays.parallelSort(array);
        assertEquals(1, array[0]);
        assertEquals(100_000, array[array.length - 1]);
    }

    @Test
    void parallelStreamSum() {
        int sum = IntStream.range(1, 10000)
                .parallel()
                .sum();
        assertEquals(49995000, sum);
    }

    @Test
    void parallelStreamFilter() {
        long count = IntStream.range(1, 10000)
                .parallel()
                .filter(n -> n % 2 == 0)
                .count();
        assertEquals(4999, count);
    }

    @Test
    void parallelStreamMaintainsCorrectness() {
        int[] result = IntStream.range(1, 100)
                .parallel()
                .map(n -> n * 2)
                .sorted()
                .toArray();
        assertEquals(2, result[0]);
        assertEquals(198, result[result.length - 1]);
        assertTrue(IntStream.range(0, result.length).allMatch(i -> result[i] == (i + 1) * 2));
    }
}