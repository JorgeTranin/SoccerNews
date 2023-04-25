package com.soccernews.domain;

public class News {
    private String title;
    private String description;
    private String textNew;

    public News(String title, String description, String textNew) {
        this.title = title;
        this.description = description;
        this.textNew = textNew;
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

    public String getTextNew() {
        return textNew;
    }

    public void setTextNew(String textNew) {
        this.textNew = textNew;
    }
}
