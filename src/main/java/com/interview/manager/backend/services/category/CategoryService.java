package com.interview.manager.backend.services.category;

import com.interview.manager.backend.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    Optional<CategoryDTO> getCategoryById(Long id);
    CategoryDTO save(CategoryDTO categoryDTO);
    CategoryDTO update(Long id, CategoryDTO categoryDTO);
    CategoryDTO patch(Long id, CategoryDTO categoryDTO);
    void delete(Long id);
}
