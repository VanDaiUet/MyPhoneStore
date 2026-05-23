package com.nwdy.phonevip.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
}
