package com.nwdy.phonevip.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
}
