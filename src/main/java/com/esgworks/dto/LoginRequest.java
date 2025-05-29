package com.esgworks.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String password;

    // Getter/Setter, 생성자 등 추가 (Lombok 쓰면 @Data 가능)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
