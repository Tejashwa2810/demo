package com.example.springap.Models;

import jakarta.persistence.Entity;

import java.util.Date;

@Entity
public class Category extends BaseModel{
    private String title;

    public Category() {}

    public Category(Long id, Date createdAt, Date updatedAt, boolean deleted, String title) {
        super(id, createdAt, updatedAt, deleted);
        this.title = title;
    }

    public Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
