package ru.job4j.grabber;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PsqlStoreTest {
    private SqlRuParse parse;

    @Before
    public void initSqlRuParse() {
        parse = new SqlRuParse();
    }

    public Connection init() {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("post.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("login"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void findById() {
        try (PsqlStore store = new PsqlStore(ConnectionRollBack.create(this.init()))) {
            Post postOne = parse.detail(
                    "https://www.sql.ru/forum/1336322/php-backend-developer-remote-do-150000-rub");
            store.save(postOne);
            Post result = store.findById(postOne.getId());
            LocalDateTime expected = LocalDateTime.of(2021, 5, 24, 16, 41);
            assertThat(result.getCreated(), is(expected));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllPosts() {
        try (PsqlStore store = new PsqlStore(ConnectionRollBack.create(this.init()))) {
            Post postOne = parse.detail(
                    "https://www.sql.ru/forum/1336322/php-backend-developer-remote-do-150000-rub");
            Post postTwo = parse.detail(
                    "https://www.sql.ru/forum/1336309/razrabotchik-net-core-150-230-tys-udalyonka");
            store.save(postOne);
            store.save(postTwo);
            List<Post> posts = new ArrayList<>(store.getAll());
            assertThat(posts.size(), is(2));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}