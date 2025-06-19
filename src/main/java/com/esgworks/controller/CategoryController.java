package com.esgworks.controller;

import com.esgworks.dto.CategoryDTO;
import com.esgworks.dto.CategoryDetailDTO;
import com.esgworks.dto.ChartDTO;
import com.esgworks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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


    // sectionId로 카테고리 리스트 조회
    @GetMapping("/by-section/{sectionId}")
    public ResponseEntity<List<CategoryDetailDTO>> getCategoriesBySectionId(@PathVariable String sectionId, @RequestParam(value = "starts-with", required = false, defaultValue = "") String startsWith) {
        List<CategoryDetailDTO> categories = categoryService.getCategoriesBySectionId(sectionId, startsWith);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> search(@RequestParam(defaultValue = "") String keyword) {
        return ResponseEntity.ok(categoryService.search(keyword));
    }

//    @GetMapping("/name")
//    public ResponseEntity<CategoryDTO> getCategoryByName(@RequestParam String categoryName) {
//        return ResponseEntity.ok(categoryService.getCategoryByName(categoryName));
//    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailDTO> getCategoryById(@PathVariable String categoryId) {
        CategoryDetailDTO category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO dto) {
        categoryService.createCategory(dto);
        return ResponseEntity.ok(dto);
    }
    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDTO dto) {
        dto.setCategoryId(categoryId);  // 경로에서 받은 categoryId를 DTO에 덮어씀
        CategoryDTO updatedDto = categoryService.updateCategory(dto);
        return ResponseEntity.ok(updatedDto);  // 진짜 저장된 값 기준으로 응답
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
