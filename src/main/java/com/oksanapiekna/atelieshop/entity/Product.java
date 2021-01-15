package com.oksanapiekna.atelieshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String article;
    private String imgSrc;
    private String description;

    private double price;
    private String season;


    private List<String> sizes;

    @ManyToOne
    private Category category;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity;
}
