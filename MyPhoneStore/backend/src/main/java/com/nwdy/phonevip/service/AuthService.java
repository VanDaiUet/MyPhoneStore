package com.nwdy.phonevip.service;

import com.nwdy.phonevip.dto.request.LoginRequest;
import com.nwdy.phonevip.dto.request.RegisterRequest;
import com.nwdy.phonevip.dto.response.TokenResponse;
import com.nwdy.phonevip.exception.AppException;
import com.nwdy.phonevip.exception.ErrorCode;
import com.nwdy.phonevip.mapper.UserMapper;
import com.nwdy.phonevip.model.Role;
import com.nwdy.phonevip.model.User;
import com.nwdy.phonevip.model.enums.ERole;
import com.nwdy.phonevip.repository.RoleRepository;
import com.nwdy.phonevip.repository.UserRepository;
import com.nwdy.phonevip.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public TokenResponse login(LoginRequest loginRequest) {
        userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        return new TokenResponse(jwtUtil.generateToken(loginRequest.getUsername()));
    }

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        } else if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));
        User user = UserMapper.INSTANCE.toUser(registerRequest);

        Role userRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }

}
