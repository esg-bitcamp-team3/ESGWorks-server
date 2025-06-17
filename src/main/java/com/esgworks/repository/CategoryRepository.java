package com.esgworks.repository;

import com.esgworks.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByCategoryId(String categoryId);
    List<Category> findAllBySectionId(String sectionId);
    List<Category> findAllBySectionIdAndCategoryIdStartingWith(String sectionId, String categoryId);

}