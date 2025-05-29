package com.esgworks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignupRequest {
    private String id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String corpId;
}
