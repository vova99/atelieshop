package com.oksanapiekna.atelieshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String size;

    @ManyToOne
    private Category category;


    @ManyToMany
    @JoinTable(
            name = "product_sizes",
            inverseJoinColumns = @JoinColumn(name = "products", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "sizes",referencedColumnName = "id")
    )
    private List<Product>  products;
}
