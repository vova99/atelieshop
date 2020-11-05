package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJPA extends JpaRepository<Order,Integer> {
}
