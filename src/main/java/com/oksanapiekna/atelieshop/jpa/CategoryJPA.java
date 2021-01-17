//package com.oksanapiekna.atelieshop.jpa;
//
//import com.oksanapiekna.atelieshop.entity.Category;
//import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface CategoryJPA extends JpaRepository<Category,Integer> {
//    @Query("select obj from Category obj where obj.statusOfEntity=?1")
//    List<Category> findByStatus(StatusOfEntity status);
//}
