package com.nwdy.phonevip.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSearchResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private double rating;
}
