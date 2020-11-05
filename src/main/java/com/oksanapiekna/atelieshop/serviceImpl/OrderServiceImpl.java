package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.OrderInfo;
import com.oksanapiekna.atelieshop.entity.StatusOfEntity;
import com.oksanapiekna.atelieshop.jpa.OrderJPA;
import com.oksanapiekna.atelieshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderJPA orderJPA;

    @Autowired
    public OrderServiceImpl(OrderJPA orderJPA) {
        this.orderJPA = orderJPA;
    }

    @Override
    public void save(OrderInfo orderInfo) {
        orderJPA.save(orderInfo);
    }

    @Override
    public OrderInfo findById(int id) {
        return orderJPA.getOne(id);
    }

    @Override
    public List<OrderInfo> findAll() {
        return orderJPA.findAll();
    }

    @Override
    public List<OrderInfo> findByStatus(StatusOfEntity status) {
        return orderJPA.findByStatus(status);
    }

    @Override
    public void deleteByID(int id) {
        orderJPA.deleteById(id);
    }
}
