package com.nwdy.phonevip.controller;

import com.nwdy.phonevip.dto.request.LoginRequest;
import com.nwdy.phonevip.dto.request.RegisterRequest;
import com.nwdy.phonevip.dto.response.ApiResponse;
import com.nwdy.phonevip.dto.response.TokenResponse;
import com.nwdy.phonevip.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> registerUser(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        ApiResponse<Void> apiResponse = ApiResponse.success(
                "Registered account successfully",
                null
        );
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> loginUser(@RequestBody LoginRequest loginRequest) {
        ApiResponse<TokenResponse> apiResponse = ApiResponse.success(
                "Login successfully",
                authService.login(loginRequest)
        );
        return ResponseEntity.ok(apiResponse);
    }

}