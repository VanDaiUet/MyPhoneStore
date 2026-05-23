package com.nwdy.phonevip.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductReviewDTO {
    private Long reviewId;
    private String comment;
    private int rating;
    private String username;
    private LocalDateTime createdAt;
}
