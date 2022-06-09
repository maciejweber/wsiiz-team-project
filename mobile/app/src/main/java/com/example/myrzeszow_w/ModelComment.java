package com.example.myrzeszow_w;

public class ModelComment {

    String id, text, created_on, author;

    public ModelComment(String id, String text, String created_on, String author) {
        this.id = id;
        this.text = text;
        this.created_on = created_on;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
