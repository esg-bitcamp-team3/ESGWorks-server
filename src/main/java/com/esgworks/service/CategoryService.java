package com.esgworks.service;

import com.esgworks.domain.Category;
import com.esgworks.dto.CategoryDTO;
import com.esgworks.dto.CategoryDetailDTO;
import com.esgworks.dto.SectionDTO;
import com.esgworks.dto.UnitDTO;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SectionService sectionService;
    private final UnitService unitService;

    // 전체 카테고리 조회
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new NotFoundException("카테고리가 존재하지 않습니다.");
        }
        return categories.stream().map(Category::toDTO).toList();
    }

    // categoryId로 단일 조회
    public CategoryDetailDTO getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("해당 카테고리가 존재하지 않습니다."));

        SectionDTO section = sectionService.getSectionById(category.getSectionId());
        UnitDTO unit = unitService.getUnitById(category.getUnitId());
        return category.toDetailDTO(section, unit);
    }

    // sectionId로 카테고리 목록 조회
    public List<CategoryDetailDTO> getCategoriesBySectionId(String sectionId, String startsWith) {
        List<Category> categories = categoryRepository.findAllBySectionIdAndCategoryIdStartingWith(sectionId, startsWith);

        if (categories.isEmpty()) {
            return new ArrayList<>();
        }

        SectionDTO section = sectionService.getSectionById(sectionId);

        return categories.stream()
                .map(category -> {
                    UnitDTO unit = unitService.getUnitById(category.getUnitId());
                    return category.toDetailDTO(section, unit);
                })
                .toList();
    }

    public List<CategoryDTO> search(String keyword) {
        List<Category> categories;

        if (keyword == null || keyword.isBlank()) {
            categories = categoryRepository.findAll(); // keyword가 없으면 전체 목록 반환
        } else {
            categories = categoryRepository.search(keyword); // 커스텀 검색
        }

        return categories.stream().map(Category::toDTO).toList();
    }

    // 이름으로 조회
    public List<CategoryDTO> getCategoryByName(String categoryName) {
        List<Category> categories ;
        if(categoryName == null || categoryName.isBlank()) {
            categories = categoryRepository.findAll();
        }else{
            categories = categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName);
        }
        return categories.stream()
          .map(Category::toDTO)
          .toList();
    }


    public List<CategoryDTO> getCategoryBySectionId(String sectionId, String categoryName) {
        List<Category> categories ;
        if(categoryName == null || categoryName.isBlank()) {
            categories = categoryRepository.findAllBySectionId(sectionId);
        }else{
            categories = categoryRepository.findAllBySectionIdAndCategoryNameContainingIgnoreCase(sectionId, categoryName);
        }
        return categories.stream()
          .map(Category::toDTO)
          .toList();
    }

    public List<CategoryDTO> getCategoryBySectionIdAndCategoryName(String sectionId, String categoryName) {
        List<Category> categories  = categoryRepository.findAllBySectionIdAndCategoryNameContainingIgnoreCase(sectionId, categoryName);
        return categories.stream().map(Category::toDTO).toList();
    }
}
