package com.nwdy.phonevip.service;

import com.nwdy.phonevip.dto.request.ProductReviewRequest;
import com.nwdy.phonevip.exception.AppException;
import com.nwdy.phonevip.exception.ErrorCode;
import com.nwdy.phonevip.model.OrderItem;
import com.nwdy.phonevip.model.Review;
import com.nwdy.phonevip.repository.OrderItemRepository;
import com.nwdy.phonevip.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final OrderItemRepository orderItemRepository;

    public void addReview(ProductReviewRequest request) {
        OrderItem orderItem = orderItemRepository.findById(request.getOrderItemId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        // TODO: Validate this orderItem belong to the current user

        if (reviewRepository.existsByOrderItem_Id(orderItem.getId())) {
            throw new AppException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        Review review = new Review();
        review.setOrderItem(orderItem);
        review.setComment(request.getComment());
        review.setRating(request.getRating());
        reviewRepository.save(review);

        // TODO: Update the rating value of product
    }

}
