package com.interview.manager.backend.services.category;

import com.interview.manager.backend.dto.RequestCategoryDto;
import com.interview.manager.backend.dto.RequestEditCategoryDto;
import com.interview.manager.backend.dto.ResponseCategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<ResponseCategoryDto> getAllCategoriesReversed();

    Optional<ResponseCategoryDto> getCategoryById(Long id);

    ResponseCategoryDto createCategory(RequestCategoryDto requestCategoryDTO);

    ResponseCategoryDto editCategory(RequestEditCategoryDto requestCategoryDTO);

    void deleteCategoryById(Long id);
}
