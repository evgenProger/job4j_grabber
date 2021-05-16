package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Post {
    private int id;
    private String name;
    private String link;
    private String text;
    private String created;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public static void main(String[] args) throws IOException {
        Post post = new Post();
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements links = row.select("a[href]");
        String link = links.get(11).attr("href");
        Document forum = Jsoup.connect(link).get();
        Elements text = forum.select(".msgBody");
        post.setText(text.get(1).text());
        Elements date = forum.select(".msgFooter");
        post.setCreated(date.get(0).text().substring(0, 16));
        String[] txt = post.getText().split("\\.");
        for (String s: txt) {
            System.out.println(s);
        }
        System.out.println(post.getCreated());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
