package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.Order;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;

import java.util.List;

public interface CategoryService {
    void save(Category category);
    Category findById(int id);
    List<Category> findAll();
    List<Category> findByStatus(StatusOfEntity status);
    void deleteByID(int id);
}
