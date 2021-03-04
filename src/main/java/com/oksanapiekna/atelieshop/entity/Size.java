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

    private Category category;


    @ManyToMany
    @JoinTable(
            name = "product_size",
            inverseJoinColumns = @JoinColumn(name = "product", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "size",referencedColumnName = "id")
    )
    private List<Product>  products;

    @OneToMany
    private List<OrderDetails> orderDetails;

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
