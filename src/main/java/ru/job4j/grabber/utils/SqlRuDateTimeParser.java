package ru.job4j.grabber.utils;

import java.io.StringReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.HashMap.*;

public class SqlRuDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) {
        LocalDate localDate;
        LocalTime time;
        String[] arrDate = parse.split(" ");
        LocalDateTime date;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MM yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        Map<String, String> months = new HashMap<>(Map.of(
                "янв", "01",
                "фев", "02",
                "мар", "03",
                "апр", "04",
                "май", "05",
                "июн", "06",
                "июл", "07",
                "авг", "08",
                "сен", "09",
                "окт", "10"
        ));
        months.put("ноя", "11");
        months.put("дек", "12");
        if (parse.contains("сегодня")) {
            localDate = LocalDate.of(
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonth(),
                    LocalDate.now().getDayOfMonth());
            time = LocalTime.parse(arrDate[1], timeFormatter);

        } else if (parse.contains("вчера")) {
            localDate = LocalDate.of(
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonth(),
                    LocalDate.now().getDayOfMonth()).minusDays(1);
            time = LocalTime.parse(arrDate[1], timeFormatter);
        }
        else {
            String timePeriod = parse.substring(9);
            String datePeriod = parse.substring(0, 8);
            datePeriod = datePeriod.replaceAll(arrDate[1], months.get(arrDate[1]));
            localDate = LocalDate.parse(datePeriod, dateTimeFormatter);
            time = LocalTime.parse(timePeriod, timeFormatter);
        }
        date = LocalDateTime.of(localDate, time);
        return date;
    }
}
