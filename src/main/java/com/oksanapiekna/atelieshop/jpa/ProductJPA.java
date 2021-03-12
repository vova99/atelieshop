package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.Category;
import com.oksanapiekna.atelieshop.entity.Product;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.entity.TypeOfProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductJPA extends JpaRepository<Product,Integer> {
    @Query("select obj from Product obj where obj.statusOfEntity=?1")
    List<Product> findByStatus(StatusOfEntity status);

    List<Product> findFirst10ByTypeOfProductId(int typeId);
    List<Product> findFirst5ByCategoryAndTypeOfProductIdIsNot(Category category,int typeId);


    List<Product> findBySizesId(int id);
}
