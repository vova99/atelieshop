package com.oksanapiekna.atelieshop.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

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
    private String description;

    private double price;
    private String season;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] img;
    private String imgType;
    private String imgName;


    @ManyToMany
    @JoinTable(
            name = "product_sizes",
            joinColumns = @JoinColumn(name = "products", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sizes",referencedColumnName = "id")
    )
    private List<Size> sizes;

    @ManyToMany
    @JoinTable(name = "products_orders",
            joinColumns = @JoinColumn(name = "productList"),
            inverseJoinColumns = @JoinColumn(name = "orders"))
    private List<OrderInfo> orders;
//
//    @ManyToOne
//    private TypeOfProduct typeOfProduct;

//    @ManyToOne
//    private Category category;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;
}
