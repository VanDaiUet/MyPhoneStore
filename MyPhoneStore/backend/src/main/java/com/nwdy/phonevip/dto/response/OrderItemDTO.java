package com.nwdy.phonevip.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long orderItemId;
    private String productName;
    private BigDecimal price;
    private int quantity;
}
