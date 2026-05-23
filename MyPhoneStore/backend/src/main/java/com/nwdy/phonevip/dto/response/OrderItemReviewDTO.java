package com.nwdy.phonevip.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemReviewDTO {
    private Long orderItemId;
    private String productName;
    private BigDecimal productPrice;
    private String productImageUrl;
    private Integer rating;
    private String comment;
}
