package com.esgworks.repository;

import com.esgworks.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByCategoryId(String categoryId);
    List<Category> findAllBySectionId(String sectionId);

}