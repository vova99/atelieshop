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


    private String sizes;

    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails;

    @ManyToOne
    private TypeOfProduct typeOfProduct;

    private Category category;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;
}
