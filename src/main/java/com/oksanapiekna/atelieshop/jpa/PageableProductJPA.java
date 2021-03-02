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
    @Query("select p from Product p where p.statusOfEntity=?1 and p.typeOfProduct.id in ?2 and p.season in ?3  " +
            "and p.sizes.size in ?4 and p.price between ?5 and ?6")
    List<Product> getFilterProduct(StatusOfEntity status, int[] typeOfProduct, String[] season, int[] sizes, int maxPrice, int minPrice, Pageable pageable);
}
