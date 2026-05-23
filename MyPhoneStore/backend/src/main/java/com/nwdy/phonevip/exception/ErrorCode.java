package com.nwdy.phonevip.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(5000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),

    // Not found (10xx)
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(1002, "Product not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1003, "Role not found", HttpStatus.NOT_FOUND),
    MANUFACTURER_NOT_FOUND(1004, "Manufacturer not found", HttpStatus.NOT_FOUND),
    CART_NOT_FOUND(1005, "Cart not found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(1006, "Order not found", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND(1007, "Cart item not found", HttpStatus.NOT_FOUND),

    // Bad request(11xx)
    BAD_REQUEST(1100, "Bad request", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1101, "Incorrect password", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORMAT(1102, "Invalid password format", HttpStatus.BAD_REQUEST),
    PASSWORD_SAME_AS_OLD(1103, "New password is the same as the old password", HttpStatus.BAD_REQUEST),
    INCORRECT_OLD_PASSWORD(1104, "Incorrect old password", HttpStatus.BAD_REQUEST),

    USERNAME_ALREADY_EXISTS(1110, "Username already exists", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(1111, "Email already exists", HttpStatus.BAD_REQUEST),
    PRODUCT_ALREADY_EXISTS(1112, "Product already exists", HttpStatus.BAD_REQUEST),
    REVIEW_ALREADY_EXISTS(1113, "Product review already exists", HttpStatus.BAD_REQUEST),

    // Unauthenticated (12xx)
    AUTHENTICATION_FAILED(1200, "Authentication failed", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_ACCESS(1201, "Unauthorized access", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED(1202, "Token expired", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(1203, "Invalid token", HttpStatus.UNAUTHORIZED),

    ;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
