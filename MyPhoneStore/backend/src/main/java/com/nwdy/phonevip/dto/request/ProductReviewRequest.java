package com.nwdy.phonevip.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProductReviewRequest {
    private Long orderItemId;
    @Length(min = 1, max = 255, message = "The length of the comment must be between 1 and 255 characters.")
    private String comment;
    @Min(value = 1, message = "The value of rating must be at lease 1.")
    @Max(value = 5, message = "The value of rating must not exceed 5.")
    private int rating;
}
