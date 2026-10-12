package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JDK 8 新特性测试：重复注解（@Repeatable）。
 * 验证同一元素上可多次使用同一注解，并通过反射读取所有重复注解。
 */
class RepeatingAnnotationsTest {

    @Schedule(time = "08:00")
    @Schedule(time = "12:00")
    @Schedule(time = "18:00")
    public void task() {
    }

    @Test
    void readRepeatingAnnotations() throws NoSuchMethodException {
        Method method = RepeatingAnnotationsTest.class.getMethod("task");
        Schedule[] schedules = method.getAnnotationsByType(Schedule.class);
        assertEquals(3, schedules.length);
        assertEquals("08:00", schedules[0].time());
        assertEquals("12:00", schedules[1].time());
        assertEquals("18:00", schedules[2].time());
    }

    @Test
    void readContainerAnnotation() throws NoSuchMethodException {
        Method method = RepeatingAnnotationsTest.class.getMethod("task");
        Schedules container = method.getAnnotation(Schedules.class);
        assertEquals(3, container.value().length);
    }

    @Repeatable(Schedules.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Schedule {
        String time();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Schedules {
        Schedule[] value();
    }
}