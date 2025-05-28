package com.esgworks.service;

import com.esgworks.domain.Category;
import com.esgworks.dto.CategoryDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 전체 카테고리 조회
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new NotFoundException("카테고리가 존재하지 않습니다.");
        }
        return categories.stream().map(Category::toDTO).toList();
    }

    // categoryId로 단일 조회
    public CategoryDTO getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("해당 카테고리가 존재하지 않습니다."));
        return category.toDTO();
    }

    // sectionId로 카테고리 목록 조회
    public List<CategoryDTO> getCategoriesBySectionId(String sectionId) {
        List<Category> categories = categoryRepository.findAllBySectionId(sectionId);
        if (categories.isEmpty()) {
            throw new NotFoundException("해당 섹션에 대한 카테고리가 존재하지 않습니다.");
        }
        return categories.stream().map(Category::toDTO).toList();
    }
}
