package com.nwdy.phonevip.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
    private List<CartItemDTO> cartItems;
    private BigDecimal totalPrice;
}
