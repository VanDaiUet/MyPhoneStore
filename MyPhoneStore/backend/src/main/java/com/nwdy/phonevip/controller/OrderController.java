package com.nwdy.phonevip.controller;

import com.nwdy.phonevip.dto.response.*;
import com.nwdy.phonevip.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // For USER role

    @GetMapping("/purchase")
    public ResponseEntity<ApiResponse<OrderInfoResponse>> purchase() {
        return ResponseEntity.ok(ApiResponse.success(
                "Preparing order to purchase. ...",
                orderService.purchase()
        ));
    }

    @GetMapping("/orders/history")
    public ResponseEntity<ApiResponse<List<OrderHistoryDTO>>> getOrderHistory() {
        return ResponseEntity.ok(ApiResponse.success(
                "Historical orders found",
                orderService.getOrderHistory()
        ));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<List<OrderItemReviewDTO>>> getOrderItemReviews(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Order items found",
                orderService.getOrderItemReviews(orderId)
        ));
    }

    // For ADMIN role

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success(
                "Orders found",
                orderService.getOrders()
        ));
    }

}
