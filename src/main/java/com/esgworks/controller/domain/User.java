package com.esgworks.controller.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "corp_id")
    private Corporation corporation;

    // getter, setter, constructor 등
}
