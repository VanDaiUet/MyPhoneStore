package com.nwdy.phonevip.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwdy.phonevip.dto.response.ApiResponse;
import com.nwdy.phonevip.exception.ErrorCode;
import com.nwdy.phonevip.model.CustomUserDetails;
import com.nwdy.phonevip.service.CustomUserDetailsService;
import com.nwdy.phonevip.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = jwtUtil.parseToken(request);

            // TODO: Revoking token
            // Blacklist

            if (token != null) {
                String username = jwtUtil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    CustomUserDetails customUserDetails = customUserDetailsService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(token, customUserDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                customUserDetails, null, customUserDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            handleJwtException(response, ErrorCode.TOKEN_EXPIRED);
        } catch (SignatureException | MalformedJwtException e) {
            handleJwtException(response, ErrorCode.INVALID_TOKEN);
        } catch (Exception e) {
            handleJwtException(response, ErrorCode.AUTHENTICATION_FAILED);
        }
        filterChain.doFilter(request, response);
    }

    private void handleJwtException(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.failure(
                errorCode.getMessage(),
                errorCode.getCode()
        );

        OutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, apiResponse);
        outputStream.flush();
    }
}
