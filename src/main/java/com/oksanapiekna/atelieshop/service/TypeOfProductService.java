package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.entity.TypeOfProduct;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TypeOfProductService {
    TypeOfProduct save(TypeOfProduct type);
    TypeOfProduct update(TypeOfProduct type);
    TypeOfProduct create(String nameType,String categoryName);
    TypeOfProduct findById(int id);
    List<TypeOfProduct> findAll();
    void deleteByID(int id);
}
