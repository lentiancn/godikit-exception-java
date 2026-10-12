package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDK 8 新特性测试：Stream Collectors。
 * 覆盖 toList/toSet/toMap、joining、groupingBy、partitioningBy、统计汇总。
 */
class StreamCollectorsTest {

    private final List<Employee> employees = Arrays.asList(
            new Employee("Alice", "Engineering", 5000),
            new Employee("Bob", "Engineering", 6000),
            new Employee("Charlie", "Sales", 4000),
            new Employee("Dave", "Sales", 4500)
    );

    @Test
    void toListAndToSet() {
        List<String> names = employees.stream().map(Employee::getName).collect(Collectors.toList());
        assertEquals(4, names.size());

        Set<String> departments = employees.stream()
                .map(Employee::getDepartment)
                .collect(Collectors.toSet());
        assertEquals(2, departments.size());
        assertTrue(departments.contains("Engineering"));
    }

    @Test
    void toMap() {
        Map<String, Integer> salaryByName = employees.stream()
                .collect(Collectors.toMap(Employee::getName, Employee::getSalary));
        assertEquals(5000, salaryByName.get("Alice"));
        assertEquals(6000, salaryByName.get("Bob"));
    }

    @Test
    void joining() {
        String result = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", "));
        assertEquals("Alice, Bob, Charlie, Dave", result);

        String withPrefixSuffix = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", ", "[", "]"));
        assertEquals("[Alice, Bob, Charlie, Dave]", withPrefixSuffix);
    }

    @Test
    void groupingBy() {
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        assertEquals(2, byDept.size());
        assertEquals(2, byDept.get("Engineering").size());
        assertEquals(2, byDept.get("Sales").size());

        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.averagingInt(Employee::getSalary)));
        assertEquals(5500.0, avgSalaryByDept.get("Engineering"));
        assertEquals(4250.0, avgSalaryByDept.get("Sales"));
    }

    @Test
    void partitioningBy() {
        Map<Boolean, List<Employee>> partitioned = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 4500));
        assertEquals(2, partitioned.get(true).size());
        assertEquals(2, partitioned.get(false).size());
    }

    @Test
    void summarizingInt() {
        IntSummaryStatistics stats = employees.stream()
                .collect(Collectors.summarizingInt(Employee::getSalary));
        assertEquals(4, stats.getCount());
        assertEquals(19500, stats.getSum());
        assertEquals(6000, stats.getMax());
        assertEquals(4000, stats.getMin());
        assertEquals(4875.0, stats.getAverage());
    }

    @Test
    void countingAndMaxBy() {
        long count = employees.stream().collect(Collectors.counting());
        assertEquals(4, count);
    }

    static class Employee {
        private final String name;
        private final String department;
        private final int salary;

        Employee(String name, String department, int salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        String getName() {
            return name;
        }

        String getDepartment() {
            return department;
        }

        int getSalary() {
            return salary;
        }
    }
}