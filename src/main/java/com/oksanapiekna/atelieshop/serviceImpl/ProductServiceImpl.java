package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.jpa.ProductJPA;
import com.oksanapiekna.atelieshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductJPA productJPA;

    @Autowired
    public ProductServiceImpl(ProductJPA productJPA) {
        this.productJPA = productJPA;
    }

    @Override
    public void save(Product product) {
        productJPA.save(product);
    }

    @Override
    public Product findById(int id) {
        return productJPA.getOne(id);
    }

    @Override
    public List<Product> findAll() {
        return productJPA.findAll();
    }

    @Override
    public List<Product> findByStatus(StatusOfEntity status) {
        return productJPA.findByStatus(status);
    }

    @Override
    public void deleteByID(int id) {
        productJPA.deleteById(id);
    }
}
