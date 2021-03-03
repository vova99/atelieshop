package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageableProductJPA extends PagingAndSortingRepository<Product, Integer> {
    @Query("select p from Product p where p.statusOfEntity=?1 and p.typeOfProduct.id in ?2 and p.season in ?3  ")
//            "and p.price<=?4 and p.price>=?5")
    List<Product> getFilterProduct(StatusOfEntity status,List<Integer> typeOfProduct,List<String> season,Pageable pageable);
}
