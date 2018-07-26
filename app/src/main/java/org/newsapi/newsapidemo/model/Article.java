package org.newsapi.newsapidemo.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Article implements Serializable {
    public Source source;
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    private String publishedAt;

    private static final SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    public String getPublishedAt() {
//        try {
//            return input.parse(publishedAt);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
