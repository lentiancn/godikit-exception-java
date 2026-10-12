package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JDK 8 新特性测试：接口默认方法与静态方法。
 * 验证 default 方法的默认实现、覆盖、冲突解决，以及接口静态方法调用。
 */
class DefaultMethodsTest {

    @Test
    void defaultMethodInherited() {
        Car car = new Car();
        assertEquals("Vehicle starting", car.start());
    }

    @Test
    void defaultMethodOverridden() {
        SportsCar car = new SportsCar();
        assertEquals("SportsCar starting", car.start());
    }

    @Test
    void defaultMethodConflictResolution() {
        AlarmCar car = new AlarmCar();
        assertEquals("Vehicle starting + Alarm starting", car.start());
    }

    @Test
    void staticMethodInInterface() {
        assertEquals("Godikit Motors", Vehicle.manufacturer());
    }

    interface Vehicle {
        static String manufacturer() {
            return "Godikit Motors";
        }

        default String start() {
            return "Vehicle starting";
        }
    }

    interface Alarm {
        default String start() {
            return "Alarm starting";
        }
    }

    static class Car implements Vehicle {
    }

    static class SportsCar implements Vehicle {
        @Override
        public String start() {
            return "SportsCar starting";
        }
    }

    static class AlarmCar implements Vehicle, Alarm {
        @Override
        public String start() {
            return Vehicle.super.start() + " + " + Alarm.super.start();
        }
    }
}
