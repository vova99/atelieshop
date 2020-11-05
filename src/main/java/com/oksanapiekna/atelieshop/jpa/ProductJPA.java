package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJPA extends JpaRepository<Product,Integer> {
}
