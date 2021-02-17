package com.oksanapiekna.atelieshop.jpa;

import com.oksanapiekna.atelieshop.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OrderDetailsJPA extends JpaRepository<OrderDetails,Integer> {
    @Query("select o from OrderDetails o where o.orderInfo.id=?1 and o.product.id=?2")
    OrderDetails findByOrderInfoByProduct(UUID orderId, int productId);
}
