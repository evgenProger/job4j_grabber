package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Post {
    private String content;
    private String date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static void main(String[] args) throws IOException {
        Post post = new Post();
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements links = row.select("a[href]");
        String link = links.get(11).attr("href");
        Document forum = Jsoup.connect(link).get();
        Elements text = forum.select(".msgBody");
        post.setContent(text.get(1).text());
        Elements date = forum.select(".msgFooter");
        post.setDate(date.get(0).text().substring(0, 16));
        String[] txt = post.getContent().split("\\.");
        for (String s: txt) {
            System.out.println(s);
        }
        System.out.println(post.getDate());
    }
}
