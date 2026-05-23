package com.nwdy.phonevip.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemUpdateRequest {
    @Positive
    private int quantity;
    private boolean selected;
}
