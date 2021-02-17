package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.OrderInfo;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderJPA extends JpaRepository<OrderInfo, UUID> {
    @Query("select obj from OrderInfo obj where obj.statusOfEntity=?1")
    List<OrderInfo> findByStatus(StatusOfEntity status);
}
