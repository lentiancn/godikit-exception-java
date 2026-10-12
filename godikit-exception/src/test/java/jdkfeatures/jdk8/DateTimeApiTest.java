package jdkfeatures.jdk8;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDK 8 新特性测试：java.time 日期时间 API。
 * 覆盖 LocalDate/LocalTime/LocalDateTime、Instant、Duration/Period、格式化、时区、计算。
 */
class DateTimeApiTest {

    @Test
    void localDateCreation() {
        LocalDate date = LocalDate.of(2026, Month.OCTOBER, 12);
        assertEquals(2026, date.getYear());
        assertEquals(Month.OCTOBER, date.getMonth());
        assertEquals(12, date.getDayOfMonth());
        assertEquals("2026-10-12", date.toString());
    }

    @Test
    void localTimeCreation() {
        LocalTime time = LocalTime.of(14, 30, 45);
        assertEquals(14, time.getHour());
        assertEquals(30, time.getMinute());
        assertEquals(45, time.getSecond());
        assertEquals("14:30:45", time.toString());
    }

    @Test
    void localDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2026, 10, 12, 14, 30);
        assertEquals(2026, dateTime.getYear());
        assertEquals(14, dateTime.getHour());
    }

    @Test
    void dateCalculation() {
        LocalDate date = LocalDate.of(2026, 10, 12);
        LocalDate nextWeek = date.plusDays(7);
        assertEquals(19, nextWeek.getDayOfMonth());

        LocalDate lastMonth = date.minusMonths(1);
        assertEquals(Month.SEPTEMBER, lastMonth.getMonth());
    }

    @Test
    void periodBetweenDates() {
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 10, 12);
        Period period = Period.between(start, end);
        assertEquals(9, period.getMonths());
        assertEquals(11, period.getDays());
    }

    @Test
    void durationBetweenTimes() {
        LocalTime start = LocalTime.of(10, 0);
        LocalTime end = LocalTime.of(12, 30);
        Duration duration = Duration.between(start, end);
        assertEquals(150L, duration.toMinutes());
        // toHours() 返回 long，舍弃不足一小时的部分
        assertEquals(2L, duration.toHours());
        assertEquals(2.5, duration.toMinutes() / 60.0, 0.001);
    }

    @Test
    void formattingAndParsing() {
        LocalDate date = LocalDate.of(2026, 10, 12);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        assertEquals("2026/10/12", date.format(formatter));

        LocalDate parsed = LocalDate.parse("2026/10/12", formatter);
        assertEquals(date, parsed);
    }

    @Test
    void instantAndZonedDateTime() {
        Instant instant = Instant.now();
        assertTrue(instant.getEpochSecond() > 0);

        ZonedDateTime zoned = ZonedDateTime.of(
                LocalDateTime.of(2026, 10, 12, 14, 30),
                ZoneId.of("America/New_York")
        );
        assertEquals("America/New_York", zoned.getZone().getId());
    }

    @Test
    void chronoUnitDifference() {
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2027, 1, 1);
        long days = ChronoUnit.DAYS.between(start, end);
        assertEquals(365, days);
    }

    @Test
    void dateComparison() {
        LocalDate earlier = LocalDate.of(2026, 1, 1);
        LocalDate later = LocalDate.of(2026, 12, 31);
        assertTrue(earlier.isBefore(later));
        assertTrue(later.isAfter(earlier));
        assertTrue(earlier.isEqual(LocalDate.of(2026, 1, 1)));
    }
}