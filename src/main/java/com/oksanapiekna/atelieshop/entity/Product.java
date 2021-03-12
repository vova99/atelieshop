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
    private double newPrice;
    private boolean productForSales;
    private String season;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] img;
    private String imgType;
    private String imgName;


    @ManyToMany
    @JoinTable(
            name = "product_size",
            joinColumns = @JoinColumn(name = "product", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "size",referencedColumnName = "id")
    )
    private List<Size>  sizes;

    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails;

    @ManyToOne
    private TypeOfProduct typeOfProduct;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", article='" + article + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", statusOfEntity=" + statusOfEntity +
                "}";
    }
}
