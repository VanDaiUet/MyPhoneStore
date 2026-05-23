package com.nwdy.phonevip.repository;

import com.nwdy.phonevip.dto.response.ProductReviewDTO;
import com.nwdy.phonevip.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
        SELECT new com.nwdy.phonevip.dto.response.ProductReviewDTO(
            r.id,
            r.comment,
            r.rating,
            u.username,
            r.createdAt
        )
        FROM Review r
        JOIN r.orderItem oi
        JOIN oi.product p
        JOIN oi.order o
        JOIN o.user u
        WHERE p.id = :productId
        ORDER BY r.createdAt DESC
    """)
    List<ProductReviewDTO> findProductReviewDTOByProductId(@Param("productId") Long productId);

    boolean existsByOrderItem_Id(Long orderItemId);
}
