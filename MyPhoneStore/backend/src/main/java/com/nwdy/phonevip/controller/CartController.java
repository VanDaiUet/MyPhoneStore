package com.nwdy.phonevip.controller;

import com.nwdy.phonevip.dto.request.AddToCartRequest;
import com.nwdy.phonevip.dto.request.CartItemUpdateRequest;
import com.nwdy.phonevip.dto.response.ApiResponse;
import com.nwdy.phonevip.dto.response.CartResponse;
import com.nwdy.phonevip.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<CartResponse>> getCart() {
        return ResponseEntity.ok(ApiResponse.success(
                "Cart found",
                cartService.getCartItems()
        ));
    }

    @PostMapping("/me/products")
    public ResponseEntity<ApiResponse<Void>> addProductToCart(@Valid @RequestBody AddToCartRequest request) {
        cartService.addCartItem(request);
        return ResponseEntity.ok(ApiResponse.success(
                "Added product successfully",
                null
        ));
    }

    @PutMapping("/me/cartItems/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemUpdateRequest request
    ) {
        cartService.updateCartItem(cartItemId, request);
        return ResponseEntity.ok(ApiResponse.success(
                "Updated cart item successfully",
                null
        ));
    }

    @DeleteMapping("/me/cartItems/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeCartItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok(ApiResponse.success(
                "Deleted cart item successfully",
                null
        ));
    }
}
