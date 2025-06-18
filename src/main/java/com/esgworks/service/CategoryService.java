package com.esgworks.service;

import com.esgworks.domain.Category;
import com.esgworks.domain.Chart;
import com.esgworks.dto.*;
import com.esgworks.exceptions.NotFoundException;
import com.esgworks.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
//    public List<CategoryDTO> getCategoryByName(String categoryName) {
//        List<Category> categories ;
//        if(categoryName == null || categoryName.isBlank()) {
//            categories = categoryRepository.findAll();
//        }else{
//            categories = categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName);
//        }
//        return categories.stream()
//          .map(Category::toDTO)
//          .toList();
//    }
    public List<CategoryDTO> getCategoryByName(String categoryName) {
        List<Category> categories = categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName);

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
//카테고리 생성
    public void createCategory(CategoryDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new NotFoundException("로그인하세요");
        }
        Optional<Category> optionalCategory = categoryRepository.findByCategoryId(dto.getCategoryId());
        if(optionalCategory.isPresent()){
            throw new NotFoundException("이미 존재하는 카테고리입니다.");
        }

        Category category = Category.builder()
                .categoryId(dto.getCategoryId())
                .categoryName(dto.getCategoryName())
                .description(dto.getDescription())
                .sectionId(dto.getSectionId())
                .unitId(dto.getUnitId())
                .build();
        categoryRepository.save(category);
    }


    // Category 엔티티를 CategoryDTO로 변환하는 메서드
    public CategoryDTO toDto(Category category) {
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())        // ID
                .categoryName(category.getCategoryName())    // 카테고리 이름
                .description(category.getDescription())      // 설명
                .sectionId(category.getSectionId())          // 섹션 ID
                .unitId(category.getUnitId())                // 단위 ID
                .build();                                    // DTO 완성
    }

    // 카테고리 정보를 업데이트하는 메서드
    public CategoryDTO updateCategory(CategoryDTO dto) {
        // 현재 로그인한 사용자의 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없거나 로그인 안 된 상태면 예외 발생
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotFoundException("로그인하세요");
        }

        // categoryId로 기존 카테고리를 DB에서 찾아오기
        Category category = categoryRepository.findByCategoryId(dto.getCategoryId())
                .orElseThrow(() -> new NotFoundException("해당 카테고리를 찾을 수 없습니다."));

        // 기존 카테고리 정보를 전달받은 DTO 값으로 수정
        category.setCategoryName(dto.getCategoryName());      // 이름 수정
        category.setDescription(dto.getDescription());        // 설명 수정
        category.setSectionId(dto.getSectionId());            // 섹션 ID 수정
        category.setUnitId(dto.getUnitId());                  // 단위 ID 수정

        // 수정된 카테고리를 DB에 저장 (실제로 update 수행)
        Category updatedCategory = categoryRepository.save(category);

        // 저장된 카테고리 엔티티를 DTO로 변환해서 리턴
        return toDto(updatedCategory);
    }

    public void deleteCategory(String categoryId) {
        Category existing = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다."));
        categoryRepository.delete(existing);
    }

}


