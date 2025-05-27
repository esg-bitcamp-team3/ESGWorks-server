package com.esgworks.controller.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "users")
public class UserDocument {
    @Id
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;

    private String corpId;
}
