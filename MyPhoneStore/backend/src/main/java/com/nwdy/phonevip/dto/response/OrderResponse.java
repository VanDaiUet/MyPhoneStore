package com.nwdy.phonevip.dto.response;

import com.nwdy.phonevip.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String orderCode;
    private String transactionNo;
    private String name;
    private String phoneNumber;
    private BigDecimal totalPrice;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}
