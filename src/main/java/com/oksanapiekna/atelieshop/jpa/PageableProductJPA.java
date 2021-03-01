package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageableProductJPA extends PagingAndSortingRepository<Product, Integer> {
    @Query("select p from Product p where p.category in ?1  and p.typeOfProduct.name in ?2 " +
            "and p.season in ?3")
    List<Product> findFiltered(String[] kindOf, String[] type, String[] seasons, String[] sizes);
}
