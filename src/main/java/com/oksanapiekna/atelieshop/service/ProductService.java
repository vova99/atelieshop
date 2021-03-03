package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product save(Product product);
    Product save(Product product, MultipartFile multipartFile);
    Product update(Product product, MultipartFile multipartFile);
    Product findById(int id);
    Product changeStatus(int id, boolean status);
    List<Product> findAll();
    List<Product> findByStatus(StatusOfEntity status);
    List<Product> getFilteredProducts(String category,List<Integer> types,List<String> seasons,List<Integer> sizes,
                                      double maxPrice,double minPrice, String sortType);
    void deleteByID(int id);
}
