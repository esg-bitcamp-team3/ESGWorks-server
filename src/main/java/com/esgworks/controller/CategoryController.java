package com.esgworks.controller;

import com.esgworks.dto.CategoryDTO;
import com.esgworks.dto.CategoryDetailDTO;
import com.esgworks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // 전체 카테고리 조회
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // categoryId로 단일 조회
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailDTO> getCategoryById(@PathVariable String categoryId) {
        CategoryDetailDTO category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    // sectionId로 카테고리 리스트 조회
    @GetMapping("/by-section/{sectionId}")
    public ResponseEntity<List<CategoryDetailDTO>> getCategoriesBySectionId(@PathVariable String sectionId) {
        List<CategoryDetailDTO> categories = categoryService.getCategoriesBySectionId(sectionId);
        return ResponseEntity.ok(categories);
    }
}
