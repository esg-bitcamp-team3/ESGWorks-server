package com.esgworks.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "corporations")
public class Corporation {
    @Id
    private Long id;
}
