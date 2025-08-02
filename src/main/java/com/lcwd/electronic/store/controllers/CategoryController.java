package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMsg;
import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.services.CategoryService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Creating new category: {}", categoryDto.getTitle());
        CategoryDto createdCategory = categoryService.create(categoryDto);
        logger.info("Category created successfully with ID: {}", createdCategory.getCategoryId());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable String categoryId) {
        logger.info("Updating category with ID: {}", categoryId);
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        logger.info("Category updated successfully: {}", updatedCategory.getTitle());
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMsg> deleteCategory(@PathVariable String categoryId) {
        logger.info("Deleting category with ID: {}", categoryId);
        categoryService.delete(categoryId);
        logger.info("Category deleted successfully with ID: {}", categoryId);
        ApiResponseMsg response = ApiResponseMsg.builder()
                .message("Category deleted successfully with ID: " + categoryId)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId) {
        logger.info("Fetching category by ID: {}", categoryId);
        CategoryDto categoryDto = categoryService.get(categoryId);
        logger.info("Category fetched: {}", categoryDto.getTitle());
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        logger.info("Fetching all categories - pageNumber: {}, pageSize: {}, sortBy: {}, sortDir: {}",
                pageNumber, pageSize, sortBy, sortDir);
        PageableResponse<CategoryDto> response = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Total categories fetched: {}", response.getContent().size());
        return ResponseEntity.ok(response);
    }
}
