package com.nwdy.phonevip.dto.response;

import com.nwdy.phonevip.model.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderHistoryDTO {
    private Long orderId;
    private String orderCode;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
    private int numProduct;
    private PaymentStatus status;
    private String address;
    private String phoneNumber;

    private List<OrderItemDTO> orderItemDTOList;

    public OrderHistoryDTO(Long orderId, String orderCode, LocalDateTime orderDate, BigDecimal totalPrice,
                           PaymentStatus status, String address, String phoneNumber)
    {
        this.orderId = orderId;
        this.orderCode = orderCode;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderItemDTOList = new ArrayList<>();
    }

    public void setOrderItemDTOList(List<OrderItemDTO> orderItemDTOList) {
        this.orderItemDTOList = orderItemDTOList;
        int numProduct = 0;
        for (OrderItemDTO orderItemDTO : orderItemDTOList) {
            numProduct += orderItemDTO.getQuantity();
        }
        this.numProduct = numProduct;
    }

}
