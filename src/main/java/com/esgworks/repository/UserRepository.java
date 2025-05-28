package com.esgworks.repository;

import com.esgworks.domain.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDocument, String> {
    boolean existsById(String id);
    boolean existsByEmail(String email);

}
