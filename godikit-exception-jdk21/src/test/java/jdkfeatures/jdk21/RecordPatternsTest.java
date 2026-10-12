package jdkfeatures.jdk21;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecordPatternsTest {

    @Test
    void recordPatternCanDeconstructRecord() {
        Object value = new User("Tom", 20);

        String result = "";

        if (value instanceof User(String name, int age)) {
            result = name + ":" + age;
        }

        assertEquals("Tom:20", result);
    }

    @Test
    void nestedRecordPatternWorks() {
        Account account = new Account(new User("Alice", 30));

        String result = "";

        if (account instanceof Account(User(String name, int age))) {
            result = name + "-" + age;
        }

        assertEquals("Alice-30", result);
    }

    record User(String name, int age) {
    }

    record Account(User user) {
    }
}
