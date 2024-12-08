package com.example.springap.Models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private int id;
    private String title;

    public Category() {
    }

    public Category(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
