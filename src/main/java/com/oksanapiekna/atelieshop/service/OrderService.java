package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.OrderInfo;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderInfo save(OrderInfo orderInfo);
    OrderInfo submitOrder(OrderInfo order,String name, String phone, String description);
    OrderInfo findById(UUID id);
    List<OrderInfo> findAll();
    List<OrderInfo> findByStatus(StatusOfEntity status);
    void deleteByID(UUID id);
}
