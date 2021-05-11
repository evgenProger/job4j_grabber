package ru.job4j.html;

import java.util.Objects;

public class Post {
    String head;
    String text;
    String author;
    String date;
    String url;


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return head.equals(post.head) && text.equals(post.text) && author.equals(post.author) && date.equals(post.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, text, author, date);
    }

    @Override
    public String toString() {
        return "Post{" +
                "head='" + head + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
