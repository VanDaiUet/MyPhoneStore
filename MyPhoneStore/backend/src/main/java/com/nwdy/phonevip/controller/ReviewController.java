package com.nwdy.phonevip.controller;

import com.nwdy.phonevip.dto.request.ProductReviewRequest;
import com.nwdy.phonevip.dto.response.ApiResponse;
import com.nwdy.phonevip.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createReview(@Valid @RequestBody ProductReviewRequest request) {
        reviewService.addReview(request);
        return ResponseEntity.ok(ApiResponse.success(
                "Saved product review from the user",
                null
        ));
    }

}
