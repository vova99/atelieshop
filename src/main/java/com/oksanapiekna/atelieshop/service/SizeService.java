package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.Size;

import java.util.List;

public interface SizeService {
    Size save(Size size);
    Size update(Size size);
    Size create(String sizeName, String categoryName);
    Size findById(int id);
    List<Size> findAll();
    void deleteByID(int id);
}
