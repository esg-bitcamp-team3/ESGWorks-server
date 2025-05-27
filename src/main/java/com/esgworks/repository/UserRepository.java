package com.esgworks.repository;

import com.esgworks.domain.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {
    boolean existsByEmail(String email);
}
