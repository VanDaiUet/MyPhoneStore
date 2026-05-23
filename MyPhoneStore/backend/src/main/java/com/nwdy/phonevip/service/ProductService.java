package com.nwdy.phonevip.service;

import com.nwdy.phonevip.dto.request.ProductRequest;
import com.nwdy.phonevip.dto.response.ProductDetailResponse;
import com.nwdy.phonevip.dto.response.ProductResponse;
import com.nwdy.phonevip.dto.response.ProductReviewDTO;
import com.nwdy.phonevip.exception.AppException;
import com.nwdy.phonevip.exception.ErrorCode;
import com.nwdy.phonevip.mapper.ProductMapper;
import com.nwdy.phonevip.model.Product;
import com.nwdy.phonevip.repository.ProductRepository;
import com.nwdy.phonevip.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ReviewRepository reviewRepository;

    private final SearchService searchService;

    // Get a list of products
    public List<ProductResponse> getAllProducts(int page, int size, String sort) {

        Pageable pageable;
        if (sort.isEmpty()) {
            pageable = PageRequest.of(page, size);
        } else {
            String[] sortParams = sort.split(",");
            String sortField = sortParams[0];
            String sortDirection = sortParams.length > 1 ? sortParams[1] : "asc";

            Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

            pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        }

        Page<Product> products = productRepository.findAll(pageable);
        List<Product> productList = products.getContent();
        return productList.stream().map(ProductMapper.INSTANCE::toProductResponse).collect(Collectors.toList());
    }

    // Get a product detail
    public ProductDetailResponse getProductById(Long id) {
        ProductDetailResponse response = productRepository.findById(id)
                .map(ProductMapper.INSTANCE::toProductDetailResponse)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        List<ProductReviewDTO> reviews = reviewRepository.findProductReviewDTOByProductId(id);
        response.setReviews(reviews);
        return response;
    }

    // Add a new product
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = ProductMapper.INSTANCE.toProduct(productRequest);
        productRepository.save(product);
        searchService.saveProductToElasticSearch(product);
        return ProductMapper.INSTANCE.toProductResponse(product);
    }

    // TODO: fix this function
    // Update the product
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductMapper.INSTANCE.updateProduct(product, productRequest);
        productRepository.save(product);
        searchService.saveProductToElasticSearch(product);
        return ProductMapper.INSTANCE.toProductResponse(product);
    }

    // Remove the product
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        searchService.deleteProductFromElasticSearch(id);
        productRepository.delete(product);
    }

}
