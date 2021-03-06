package com.oksanapiekna.atelieshop.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int count;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Size size;
    @ManyToOne
    private OrderInfo orderInfo;

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }
}
