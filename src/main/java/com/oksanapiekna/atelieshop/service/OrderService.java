package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.Order;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;

import java.util.List;

public interface OrderService {
    void save(Order order);
    Order findById(int id);
    List<Order> findAll();
    List<Order> findByStatus(StatusOfEntity status);
    void deleteByID(int id);
}
