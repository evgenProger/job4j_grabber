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

  /*  public static void main(String[] args) throws IOException {
        String url = "https://www.sql.ru/forum/job-offers";
        String ln = System.lineSeparator();
        for (int i = 0; i < 5; i++) {
            Document doc = Jsoup.connect(url).get();
            Elements row = doc.select(".postslisttopic");
            Elements links = doc.select(".sort_options");
            Elements pages = links.select("a[href]");
            for (Element td : row) {
                Element href = td.child(0);
                Element par = td.parent();
                Element date = (Element) par.childNode(11);
                System.out.println(href.attr("href"));
                System.out.println(href.text() + ln + date.text());
            }
            url = pages.get(i).attr("href");
        }
    }
   */

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
