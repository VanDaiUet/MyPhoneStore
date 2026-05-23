package com.nwdy.phonevip.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
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
}
