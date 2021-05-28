package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
    private final List<Post> posts = new ArrayList<>();


    @Override
    public List<Post> list(String link) {
        for (int i = 21; i < 24; i++) {
            Post post = new Post();
            try {
                Document doc = Jsoup.connect(link).get();
                Elements row = doc.select(".postslisttopic");
                Elements links = row.select("a[href]");
                String url = links.get(i).attr("href");
                post.setLink(url);
                posts.add(post);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    @Override
    public Post detail(String link) {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        Post post = new Post();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements text = doc.select(".msgBody");
            post.setText(text.get(1).text());
            Elements date = doc.select(".msgFooter");
            int index  = date.get(0).text().indexOf("[");
            String str = date.get(0).text().substring(0, index).trim();
            post.setCreated(parser.parse(str));
            Elements name = doc.select(".messageHeader");
            post.setName(name.get(0).text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }
}
