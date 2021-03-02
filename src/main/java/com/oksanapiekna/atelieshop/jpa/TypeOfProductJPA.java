package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.TypeOfProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TypeOfProductJPA extends JpaRepository<TypeOfProduct,Integer> {
    List<TypeOfProduct> findByCategory(Category category);

}
