package ru.job4j.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SqlRuDateTimeParserTest {
    @Test
    public void whenStringContainsTodayThenParseToDate() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "сегодня, 22:29";
        LocalTime time = LocalTime.of(22, 29);
        LocalDate localDate = LocalDate.of(
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth());
        LocalDateTime expected = LocalDateTime.of(localDate, time);
        assertThat(parser.parse(date), is(expected));
    }

    @Test
    public void whenStringDateTodayThenParseToDate() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "2 ноя 19 22:29";
        LocalTime time = LocalTime.of(22, 29);
        LocalDate localDate = LocalDate.of(2019, 11, 2);
        LocalDateTime expected = LocalDateTime.of(localDate, time);
        assertThat(parser.parse(date), is(expected));
    }

    @Test
    public void whenStringContainsYesterdayThenParseToDate() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        String date = "вчера, 12:06";
        LocalTime time = LocalTime.of(12, 06);
        LocalDate localDate = LocalDate.now().minusDays(1);
        LocalDateTime expected = LocalDateTime.of(localDate, time);
        assertThat(parser.parse(date), is(expected));
    }

}