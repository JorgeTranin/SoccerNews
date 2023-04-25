package com.soccernews.domain;

public class News {
    private int id;
    private String title;
    private String description;
    private String textNews;
    private String image;
    private String linkNews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLinkNews() {
        return linkNews;
    }

    public void setLinkNews(String linkNews) {
        this.linkNews = linkNews;
    }

    public News(String title, String description, String textNews) {
        this.title = title;
        this.description = description;
        this.textNews = textNews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTextNews() {
        return textNews;
    }

    public void setTextNews(String textNews) {
        this.textNews = textNews;
    }
}
