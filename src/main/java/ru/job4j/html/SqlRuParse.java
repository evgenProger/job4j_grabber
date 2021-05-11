package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
    public static void main(String[] args) throws IOException {
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
}
