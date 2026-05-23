package com.nwdy.phonevip.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderInfoResponse {
    private List<OrderItemDTO> orderItemDTOList;
    private BigDecimal totalPrice;
    private int numProducts;
    private String phoneNumber;
    private String address;

    public OrderInfoResponse(List<OrderItemDTO> orderItemDTOList, String phoneNumber, String address) {
        this.orderItemDTOList = orderItemDTOList;
        this.numProducts = 0;
        this.totalPrice = BigDecimal.ZERO;
        for (OrderItemDTO item : orderItemDTOList) {
            this.numProducts += item.getQuantity();
            this.totalPrice = totalPrice.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
