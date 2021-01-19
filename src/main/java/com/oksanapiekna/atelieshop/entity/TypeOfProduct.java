package com.oksanapiekna.atelieshop.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class TypeOfProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @OneToMany
    private List<Product> products;

    private Category category;
}
