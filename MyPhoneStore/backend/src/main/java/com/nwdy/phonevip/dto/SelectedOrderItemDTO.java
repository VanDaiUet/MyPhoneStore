package com.nwdy.phonevip.dto;

import com.nwdy.phonevip.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SelectedOrderItemDTO {
    private Long cartItemId;
    private Product product;
    private int quantity;
}
