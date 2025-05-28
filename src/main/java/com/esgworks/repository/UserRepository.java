package com.esgworks.repository;

import com.esgworks.domain.UserDocument;
import org.apache.catalina.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {
    boolean existsByEmail(String email);
    UserDocument findByEmail(String email);
}
