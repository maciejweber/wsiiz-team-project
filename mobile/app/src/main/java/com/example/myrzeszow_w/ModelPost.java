package com.example.myrzeszow_w;

public class ModelPost {
    //variables

    String authorName, id, created_on, title, estate, category, likes_count;

    //constructor
    public ModelPost(String authorName, String id, String created_on, String title, String estate, String category, String likes_count) {
        this.authorName = authorName;
        this.id = id;
        this.created_on = created_on;
        this.title = title;
        this.estate = estate;
        this.category = category;
        this.likes_count = likes_count;
    }

    // getters and setters
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(String likes_count) {
        this.likes_count = likes_count;
    }
}
