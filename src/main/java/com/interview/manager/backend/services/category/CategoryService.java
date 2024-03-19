package com.interview.manager.backend.services.category;

import com.interview.manager.backend.dto.response.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    Optional<CategoryDTO> getCategoryById(Long id);
}
