package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.OrderInfo;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderJPA extends JpaRepository<OrderInfo,Integer> {
    @Query("select obj from OrderInfo obj where obj.statusOfEntity=?1")
    List<OrderInfo> findByStatus(StatusOfEntity status);
}
