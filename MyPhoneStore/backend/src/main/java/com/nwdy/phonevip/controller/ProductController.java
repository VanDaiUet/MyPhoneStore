package com.nwdy.phonevip.controller;

import com.nwdy.phonevip.dto.request.ProductRequest;
import com.nwdy.phonevip.dto.response.ApiResponse;
import com.nwdy.phonevip.dto.response.ProductDetailResponse;
import com.nwdy.phonevip.dto.response.ProductResponse;
import com.nwdy.phonevip.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sort
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Fetched products successfully",
                productService.getAllProducts(page, size, sort)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailResponse>> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                "Product found",
                productService.getProductById(id))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Product created successfully",
                        productService.addProduct(productRequest)
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(ApiResponse.success(
                "Product updated successfully",
                productService.updateProduct(id, productRequest)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(
                "Product deleted successfully",
                null
        ));
    }

}
