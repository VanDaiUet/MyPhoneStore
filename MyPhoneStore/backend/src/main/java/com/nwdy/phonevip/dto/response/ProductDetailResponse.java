package com.nwdy.phonevip.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDetailResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private int stock;
    private String manufacturer;
    private int ram;
    private int storage;
    private String color;
    private double rating;
    private List<ProductReviewDTO> reviews;
}
