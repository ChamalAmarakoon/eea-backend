package com.example.easynotes.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    @Setter
    @Getter
    private long id;

    @Setter @Getter private String description;

    @Setter @Getter private String title;

    @Setter @Getter private int quantity;

    @Setter @Getter private double price;

    @Setter @Getter private String image;

    @Setter @Getter private String company;
}
