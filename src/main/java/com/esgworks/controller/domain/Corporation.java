package com.esgworks.controller.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Corporation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
