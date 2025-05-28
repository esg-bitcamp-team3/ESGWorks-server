package com.esgworks.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDocument {
    @Id
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String corpId;
}
