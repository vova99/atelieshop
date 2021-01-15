package com.oksanapiekna.atelieshop.entity;

import com.amazonaws.services.dynamodbv2.xspec.S;

import javax.persistence.*;

@Entity
public class TypeOfProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne
    private Category category;
}
