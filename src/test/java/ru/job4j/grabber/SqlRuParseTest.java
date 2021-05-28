package ru.job4j.grabber;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SqlRuParseTest {
    @Test
    public void whenHasPostThenGetDate() {
        SqlRuParse parse = new SqlRuParse();
        String urlPost = "https://www.sql.ru/forum/1335709/olap-razrabotchik";
        LocalDateTime expected = LocalDateTime.of(2021, 4, 28, 16, 19);
        Post post = parse.detail(urlPost);
        assertThat(post.getCreated(), is(expected));
    }


}