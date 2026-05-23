package com.nwdy.phonevip.repository;

import com.nwdy.phonevip.dto.response.OrderHistoryDTO;
import com.nwdy.phonevip.dto.response.OrderResponse;
import com.nwdy.phonevip.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
        SELECT new com.nwdy.phonevip.dto.response.OrderHistoryDTO(
            o.id,
            o.orderCode,
            o.createdAt,
            o.totalPrice,
            o.paymentStatus,
            u.address,
            u.phoneNumber
        )
        FROM Order o
        JOIN o.user u
        WHERE u.username = :username
        ORDER BY o.createdAt DESC
    """)
    List<OrderHistoryDTO> findHistoricalOrderDTOsByUsername(@Param("username") String username);

    @Query("""
    SELECT new com.nwdy.phonevip.dto.response.OrderResponse(
        o.id,
        o.orderCode,
        o.transactionNo,
        u.name,
        u.phoneNumber,
        o.totalPrice,
        o.paymentStatus,
        o.createdAt
    )
    FROM Order o
    JOIN o.user u
    """)
    List<OrderResponse> findAllOrders();

    boolean existsByOrderCode(String orderCode);

    Optional<Order> findByOrderCode(String orderCode);
}
