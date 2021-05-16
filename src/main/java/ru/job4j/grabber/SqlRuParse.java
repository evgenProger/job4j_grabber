package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
    private final List<Post> posts= new ArrayList<>();


    @Override
    public List<Post> list(String link) {
        Post post = new Post();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            Elements links = row.select("a[href]");
            String url = links.get(21).attr("href");
            post.setLink(url);
            posts.add(post);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post detail(String link) {
        Post post = posts.get(0);
        try {
            Document doc = Jsoup.connect(link).get();
            Elements text = doc.select(".msgBody");
            post.setText(text.get(1).text());
            Elements date = doc.select(".msgFooter");
            post.setCreated(date.get(0).text().substring(0, 16));
            Elements name = doc.select(".messageHeader");
            post.setName(name.get(1).text());


        } catch (IOException e) {
            e.printStackTrace();
        }

        return post;
    }
}
