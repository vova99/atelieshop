package com.oksanapiekna.atelieshop.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;

    @ManyToOne
    private Category category;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity;
}
