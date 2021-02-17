package com.oksanapiekna.atelieshop.serviceImpl;

import com.oksanapiekna.atelieshop.entity.OrderDetails;
import com.oksanapiekna.atelieshop.jpa.OrderDetailsJPA;
import com.oksanapiekna.atelieshop.service.OrderDetailsService;
import com.oksanapiekna.atelieshop.service.OrderService;
import com.oksanapiekna.atelieshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private OrderDetailsJPA detailsJPA;
    private OrderService orderService;
    private ProductService productService;


    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return detailsJPA.save(orderDetails);
    }

    @Override
    public OrderDetails update(OrderDetails orderDetails) {
        return save(orderDetails);
    }

    @Override
    public OrderDetails addProductToOrder(UUID orderId, int productId) {
        OrderDetails orderDetails = findByOrderInfoIdAndOrderByProductId(orderId,productId);
        if (orderDetails==null){
            orderDetails = new OrderDetails();
            orderDetails.setOrderInfo(orderService.findById(orderId));
            orderDetails.setProduct(productService.findById(productId));
            orderDetails.setCount(1);
        }else{
            orderDetails.setCount(orderDetails.getCount()+1);
        }

        return save(orderDetails);
    }

    @Override
    public void deleteProductFromOrder(UUID orderId, int productId) {
        OrderDetails orderDetails = findByOrderInfoIdAndOrderByProductId(orderId,productId);
        if(orderDetails!=null){
            deleteByID(orderDetails.getId());
        }
    }

    @Override
    public OrderDetails findById(int id) {
        return detailsJPA.findById(id).orElse(null);
    }

    @Override
    public OrderDetails findByOrderInfoIdAndOrderByProductId(UUID orderId, int productId) {
        OrderDetails orderDetails = detailsJPA.findByOrderInfoByProduct(orderId,productId);
        System.out.println(orderDetails.getId());
        return orderDetails;
    }

    @Override
    public List<OrderDetails> findAll() {
        return detailsJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        detailsJPA.deleteById(id);
    }
}
