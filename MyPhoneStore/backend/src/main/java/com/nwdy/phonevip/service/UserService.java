package com.nwdy.phonevip.service;

import com.nwdy.phonevip.dto.request.ChangePasswordRequest;
import com.nwdy.phonevip.dto.request.UserUpdateRequest;
import com.nwdy.phonevip.dto.response.UserResponse;
import com.nwdy.phonevip.exception.AppException;
import com.nwdy.phonevip.exception.ErrorCode;
import com.nwdy.phonevip.mapper.UserMapper;
import com.nwdy.phonevip.model.User;
import com.nwdy.phonevip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    // For role USER

    public UserResponse getUserInfo() {
        return userRepository.findByUsername(getCurrentUsername())
                .map(UserMapper.INSTANCE::toUserResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public UserResponse updateUserInfo(UserUpdateRequest request) {
        User currentUser = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        validateUsernameAndEmail(request, currentUser);
        updateUserFromRequest(request, currentUser);
        return UserMapper.INSTANCE.toUserResponse(userRepository.save(currentUser));
    }

    public void updatePassword(ChangePasswordRequest request) {
        validateNewPassword(request.getNewPassword());
        
        if (request.getOldPassword().equals(request.getNewPassword())) {
            throw new AppException(ErrorCode.PASSWORD_SAME_AS_OLD);
        }

        User user = userRepository.findByUsername(getCurrentUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                
        validatePassword(request.getOldPassword(), user.getPassword());
        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    // For role ADMIN

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::toUserResponse)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper.INSTANCE::toUserResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                
        validateUsernameAndEmail(request, user);
        updateUserFromRequest(request, user);
        return UserMapper.INSTANCE.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }


    // ####################################################################
    // Private helper methods
    // ####################################################################
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private void validatePassword(String inputPassword, String storedPassword) {
        if (!encoder.matches(inputPassword, storedPassword)) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }

    private void validateUsernameAndEmail(UserUpdateRequest request, User currentUser) {
        if (!request.getUsername().equals(currentUser.getUsername()) && 
            userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (!request.getEmail().equals(currentUser.getEmail()) && 
            userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validateNewPassword(String newPassword) {
        if (newPassword == null || newPassword.length() < 8) {
            throw new AppException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
        // Add more password validation rules here if needed
    }

    private void updateUserFromRequest(UserUpdateRequest request, User user) {
        UserMapper.INSTANCE.updateUser(request, user);
    }
}
