package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductJPA extends JpaRepository<Product,Integer> {
    @Query("select obj from Product obj where obj.statusOfEntity=?1")
    List<Product> findByStatus(StatusOfEntity status);
}
