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
    private String description;

    @ManyToMany
    private List<Category> categoryList;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity;
}
