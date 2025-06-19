package com.esgworks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordUpdateDTO {
    private String id;
    private String oldPassword;
    private String newPassword;
}