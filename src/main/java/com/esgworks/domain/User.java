package com.esgworks.domain;


import com.esgworks.dto.CorporationDTO;
import com.esgworks.dto.UserDTO;
import com.esgworks.dto.UserDetailDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String corpId;

    public UserDTO toDTO() {
        return UserDTO.builder()
          .id(id)
          .name(name)
          .email(email)
          .phoneNumber(phoneNumber)
          .corpId(corpId)
          .build();
    }
    public UserDetailDTO toDetailDTO(CorporationDTO corporationDTO) {
        return UserDetailDTO.builder()
                .id(id)
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .corporation(corporationDTO)
                .build();
    }
    public void updatePassword(String password) {
        this.password = password;
}}

