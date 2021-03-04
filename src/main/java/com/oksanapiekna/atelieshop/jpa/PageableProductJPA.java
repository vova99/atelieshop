package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface PageableProductJPA extends PagingAndSortingRepository<Product, Integer> {
  Set<Product> findByStatusOfEntityAndTypeOfProductIdInAndSeasonInAndSizesIdInAndPriceLessThanEqualAndPriceGreaterThanEqualOrderByIdDesc(StatusOfEntity status, List<Integer> type, List<String> seasons, List<Integer> sizes, double maxPrice, double minPrice);
 }
