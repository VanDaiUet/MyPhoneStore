package com.nwdy.phonevip.controller;

import com.nwdy.phonevip.dto.request.ChangePasswordRequest;
import com.nwdy.phonevip.dto.request.UserUpdateRequest;
import com.nwdy.phonevip.dto.response.ApiResponse;
import com.nwdy.phonevip.dto.response.UserResponse;
import com.nwdy.phonevip.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // For role USER

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getUserInfo() {
        return ResponseEntity.ok(ApiResponse.success(
                "User found",
                userService.getUserInfo()
        ));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserInfo(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Updated user information successfully",
                userService.updateUserInfo(request)
        ));
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@RequestBody ChangePasswordRequest request) {
        userService.updatePassword(request);
        return ResponseEntity.ok(ApiResponse.success(
                "Updated password successfully",
                null
        ));
    }

    // For role ADMIN

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(
                "All users found",
                userService.getAllUsers()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
                "User found",
                userService.getUserById(id)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Updated user information successfully",
                userService.updateUser(id, request)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        "User was deleted successfully",
                        null
                ));
    }
}
