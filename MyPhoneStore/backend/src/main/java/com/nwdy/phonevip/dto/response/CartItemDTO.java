package com.nwdy.phonevip.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private String productName;
    private String imageUrl;
    private BigDecimal price;
    private int quantity;
    private boolean selected;
}
