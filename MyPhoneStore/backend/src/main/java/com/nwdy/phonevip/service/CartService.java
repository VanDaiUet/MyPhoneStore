package com.nwdy.phonevip.service;

import com.nwdy.phonevip.dto.request.AddToCartRequest;
import com.nwdy.phonevip.dto.request.CartItemUpdateRequest;
import com.nwdy.phonevip.dto.response.CartItemDTO;
import com.nwdy.phonevip.dto.response.CartResponse;
import com.nwdy.phonevip.exception.AppException;
import com.nwdy.phonevip.exception.ErrorCode;
import com.nwdy.phonevip.model.Cart;
import com.nwdy.phonevip.model.CartItem;
import com.nwdy.phonevip.model.Product;
import com.nwdy.phonevip.model.User;
import com.nwdy.phonevip.repository.CartItemRepository;
import com.nwdy.phonevip.repository.CartRepository;
import com.nwdy.phonevip.repository.ProductRepository;
import com.nwdy.phonevip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CartItemRepository cartItemRepository;

    // Get all cart items of a user
    public CartResponse getCartItems() {
        CartResponse cartResponse = new CartResponse();
        List<CartItemDTO> items = cartItemRepository.findByUsername(getCurrentUsername());
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemDTO item : items) {
            if (item.isSelected()) {
                total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }
        cartResponse.setCartItems(items);
        cartResponse.setTotalPrice(total);
        return cartResponse;
    }

    // Add a product to a cart
    public void addCartItem(AddToCartRequest request) {
        User user = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Cart cart = cartRepository.findByUser(user)
                        .orElseGet(() -> {
                            Cart newCart = new Cart();
                            newCart.setUser(user);
                            System.out.println("Created a new cart");
                            return cartRepository.save(newCart);
                        });


        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        CartItem cartItem = (cart.getCartItems() != null ? cart.getCartItems() : new ArrayList<CartItem>()).stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                        .findFirst()
                                .orElseGet(() -> {
                                    CartItem newCartItem = new CartItem();
                                    newCartItem.setCart(cart);
                                    newCartItem.setProduct(product);
                                    newCartItem.setQuantity(0);
                                    newCartItem.setSelected(true);
                                    System.out.println("Created a new cart item");
                                    return newCartItem;
                                });
        cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        cartItemRepository.save(cartItem);
    }

    // Remove a product from cart
//    public void removeCartItem(Long cartItemId) {
//        User user = userRepository.findByUsername(getCurrentUsername())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//
//        Cart cart = cartRepository.findByUser(user)
//                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
//
//        CartItem cartItem = cart.getCartItems().stream()
//                .filter(item -> item.getId().equals(cartItemId))
//                .findFirst()
//                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));
//
//        cartItemRepository.delete(cartItem);
//    }

    public void removeCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findByIdAndCart_User_Username(cartItemId, getCurrentUsername())
                        .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.delete(cartItem);
    }

    // Update the quantity of product
    public void updateCartItem(Long cartItemId, CartItemUpdateRequest request) {
//        User user = userRepository.findByUsername(getCurrentUsername())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//
//        Cart cart = cartRepository.findByUser(user)
//                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
//
//        CartItem cartItem = cart.getCartItems().stream()
//                .filter(item -> item.getId().equals(cartItemId))
//                .findFirst()
//                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findByIdAndCart_User_Username(cartItemId, getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.setQuantity(request.getQuantity());
        cartItem.setSelected(request.isSelected());
        cartItemRepository.save(cartItem);
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
