package com.oksanapiekna.atelieshop.service;

import com.oksanapiekna.atelieshop.entity.OrderDetails;

import java.util.List;
import java.util.UUID;

public interface OrderDetailsService {
    OrderDetails save(OrderDetails orderDetails);
    void save(List<Integer> id, List<Integer> count);
    OrderDetails update(OrderDetails orderDetails);
    OrderDetails addProductToOrder(UUID orderId, int productId);
    void deleteProductFromOrder(UUID orderId, int productId);
    OrderDetails findById(int id);
    OrderDetails findByOrderInfoIdAndOrderByProductId(UUID orderId, int productId);
    List<OrderDetails> findAll();
    void deleteByID(int id);
}
