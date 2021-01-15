package com.oksanapiekna.atelieshop.entity;

import com.amazonaws.services.dynamodbv2.xspec.S;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nameOfClient;
    private String phone;
    @ManyToMany
    @JoinTable(name = "products_orders",
            inverseJoinColumns =  @JoinColumn(name = "productList"),
            joinColumns = @JoinColumn(name = "orders"))
    private List<Product> productList;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity;
}
