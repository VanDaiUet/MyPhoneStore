package com.nwdy.phonevip.repository;

import com.nwdy.phonevip.dto.response.OrderItemDTO;
import com.nwdy.phonevip.dto.response.OrderItemReviewDTO;
import com.nwdy.phonevip.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
        SELECT new com.nwdy.phonevip.dto.response.OrderItemDTO(
            oi.id,
            p.name,
            p.price,
            oi.quantity
        )
        FROM OrderItem oi
        JOIN oi.product p
        WHERE oi.order.id = :orderId
    """)
    List<OrderItemDTO> findOrderItemsByOrderId(@Param("orderId") Long orderId);

    @Query("""
        SELECT new com.nwdy.phonevip.dto.response.OrderItemReviewDTO(
            oi.id,
            p.name,
            p.price,
            p.imageUrl,
            r.rating,
            r.comment
        )
        FROM OrderItem oi
        JOIN oi.product p
        JOIN oi.order o
        LEFT JOIN Review r ON r.orderItem = oi
        WHERE o.id = :orderId
    """)
    List<OrderItemReviewDTO> findOrderItemReviewDTOByOrderId(@Param("orderId") Long orderId);
}
