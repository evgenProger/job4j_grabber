package ru.job4j.grabber;

import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {
    private Connection cnn;

    public PsqlStore(Connection cn) {
        this.init();
        this.cnn = cn;
    }


        public void init()  {
            Properties cfg = new Properties();
            try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("post.properties")) {
                cfg.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Class.forName(cfg.getProperty("driver"));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            String url = cfg.getProperty("url");
            String login = cfg.getProperty("login");
            String password = cfg.getProperty("password");
            try {
                cnn = DriverManager.getConnection(url, login, password);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }


    @Override
    public void save(Post post) {
        try (PreparedStatement ps = cnn.prepareStatement(
                "insert into post (name, link, text, created) values (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getLink());
            ps.setString(3, post.getText());
            ps.setObject(4, post.getCreated());
            ps.execute();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int value = keys.getInt(1);
                    String id = Integer.toString(value);
                    post.setId(id);
                }
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement ps = cnn.prepareStatement("select * from post")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post();
                    post.setName(resultSet.getString("name"));
                    post.setLink(resultSet.getString("link"));
                    post.setText(resultSet.getString("text"));
                    LocalDateTime localDateTime = resultSet.getTimestamp("created").toLocalDateTime();
                    post.setCreated(localDateTime);
                    int value = resultSet.getInt("id");
                    String id = Integer.toString(value);
                    post.setId(id);
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    @Override
    public Post findById(String id) throws SQLException {
        Post post = new Post();
        try (PreparedStatement ps = cnn.prepareStatement("select * from  post where id = ?")) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    post.setId(id);
                    post.setName(resultSet.getString("name"));
                    post.setLink(resultSet.getString("link"));
                    post.setText(resultSet.getString("text"));
                    post.setCreated(resultSet.getTimestamp("created").toLocalDateTime());

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }
}
