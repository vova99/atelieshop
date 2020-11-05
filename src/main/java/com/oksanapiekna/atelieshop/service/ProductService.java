package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;

import java.util.List;

public interface ProductService {
    void save(Product product);
    Product findById(int id);
    List<Product> findAll();
    List<Product> findByStatus(StatusOfEntity status);
    void deleteByID(int id);
}
