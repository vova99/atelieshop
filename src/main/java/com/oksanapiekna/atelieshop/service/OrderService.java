package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.OrderInfo;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;

import java.util.List;

public interface OrderService {
    void save(OrderInfo orderInfo);
    OrderInfo findById(int id);
    List<OrderInfo> findAll();
    List<OrderInfo> findByStatus(StatusOfEntity status);
    void deleteByID(int id);
}
