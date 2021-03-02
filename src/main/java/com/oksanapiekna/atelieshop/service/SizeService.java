package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.Size;

import java.util.List;

public interface SizeService {
    Size save(Size size);
    Size update(Size size);
    Size create(String sizeName, String categoryName);
    Size findById(int id);
    List<Size> findAll();
    List<Size> findByCategory(Category category);
    void deleteByID(int id);
}
