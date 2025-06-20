package com.esgworks.repository;

import com.esgworks.domain.Category;
import com.esgworks.repository.category.CategoryRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String>, CategoryRepositoryCustom {
    Optional<Category> findByCategoryId(String categoryId);
    List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);
    List<Category> findAllBySectionId(String sectionId);
    List<Category> search(String keyword);

    List<Category> findAllBySectionIdAndCategoryNameContainingIgnoreCase(String sectionId, String categoryName);
    List<Category> findAllBySectionIdAndCategoryIdStartingWith(String sectionId, String categoryId);
}