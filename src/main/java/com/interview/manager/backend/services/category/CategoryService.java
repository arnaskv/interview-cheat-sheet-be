package com.interview.manager.backend.services.category;

import com.interview.manager.backend.dto.ResponseCategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<ResponseCategoryDto> getAllCategories();

    Optional<ResponseCategoryDto> getCategoryById(Long id);
}
