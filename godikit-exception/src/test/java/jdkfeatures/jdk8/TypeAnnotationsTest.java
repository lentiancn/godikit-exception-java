package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JDK 8 新特性测试：类型注解（ElementType.TYPE_USE）。
 * 验证注解可应用于类型使用处（如泛型参数、new 表达式等），确保编译通过。
 */
class TypeAnnotationsTest {

    private List<@NonNull String> names;

    @Test
    void typeAnnotationOnGenericArgument() {
        // 验证类型注解可用于泛型参数（编译层面验证）
        List<@NonNull String> list = new java.util.ArrayList<>();
        list.add("hello");
        assertEquals(1, list.size());
    }

    @Test
    void typeAnnotationCanBeUsedInNewExpression() {
        // 验证类型注解可用于 new 表达式（编译层面验证）
        @NonNull String value = new @NonNull String("test");
        assertEquals("test", value);
    }

    @Test
    void typeAnnotationOnLocalVariable() {
        @Positive int number = 42;
        assertEquals(42, number);
    }

    @Test
    void typeAnnotationOnCast() {
        Object obj = "hello";
        String str = (@NonNull String) obj;
        assertEquals("hello", str);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE_USE)
    @interface NonNull {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE_USE)
    @interface Positive {
    }
}