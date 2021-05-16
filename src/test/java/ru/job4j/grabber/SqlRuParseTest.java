package ru.job4j.grabber;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SqlRuParseTest {
    @Test
    public void whenHasPostThenGetDate() {
        SqlRuParse parse = new SqlRuParse();
        String url = "https://www.sql.ru/forum/job-offers";
        List<Post> list = parse.list(url);
        String urlPost = "https://www.sql.ru/forum/1335709/olap-razrabotchik";
        Post post = parse.detail(urlPost);
        assertThat(post.getCreated(), is("28 апр 21, 16:19"));
    }


}