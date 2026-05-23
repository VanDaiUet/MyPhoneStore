package com.nwdy.phonevip.exception;

import com.nwdy.phonevip.dto.response.ApiResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<AppException>> handleAppException(AppException e){
        ErrorCode errorCode = e.getErrorCode();
        ApiResponse<AppException> apiResponse = ApiResponse.failure(
                errorCode.getMessage(),
                errorCode.getCode()
        );
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<AuthenticationException>> handleAuthenticationException(AuthenticationException e){
        ApiResponse<AuthenticationException> apiResponse = ApiResponse.failure(
                ErrorCode.INVALID_PASSWORD.getMessage(),
                ErrorCode.INVALID_PASSWORD.getCode()
        );
        return ResponseEntity.status(ErrorCode.INVALID_PASSWORD.getHttpStatus()).body(apiResponse);
    }

    // Handling errors without access
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<AccessDeniedException>> handleAccessDeniedException(AccessDeniedException ex) {
        ApiResponse<AccessDeniedException> apiResponse = ApiResponse.failure(
                ErrorCode.BAD_REQUEST.getMessage(),
                ErrorCode.BAD_REQUEST.getCode()
        );
        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getHttpStatus()).body(apiResponse);
    }

    // Handle validation errors (Invalid data)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                error -> errors.put(error.getObjectName(), error.getDefaultMessage())
        );
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation error",
                errors
        );
    }

    // Handling invalid JSON format error
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<Map<String, Object>> handleInvalidJson(HttpMessageNotReadableException ex) {
//        return buildErrorResponse(
//                HttpStatus.BAD_REQUEST,
//                "Invalid JSON format",
//                null
//        );
//    }

    // Handling data binding violations (e.g. main key, foreign key)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Database constraint violation",
                null
        );
    }

    // Handling all unstated errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex){
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                null
        );
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(
            HttpStatus status,
            String message,
            Object details
    ) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("status", status.value());
        errorMap.put("error", status.getReasonPhrase());
        errorMap.put("message", message);
        errorMap.put("timestamp", LocalDateTime.now().toString());
        if (details != null) {
            errorMap.put("details", details);
        }
        return ResponseEntity.status(status).body(errorMap);
    }
}
