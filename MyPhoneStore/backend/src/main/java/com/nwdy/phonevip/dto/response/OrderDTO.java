package com.nwdy.phonevip.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {
    private String orderCode;
    private BigDecimal totalPrice;
}
