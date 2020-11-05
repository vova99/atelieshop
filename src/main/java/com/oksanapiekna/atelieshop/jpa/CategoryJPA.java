package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJPA extends JpaRepository<Category,Integer> {
}
