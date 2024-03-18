package com.interview.manager.backend.services.Category;

import com.interview.manager.backend.dto.CategoryDTO;
import com.interview.manager.backend.models.Category;
import com.interview.manager.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Map<String, CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .collect(Collectors.toMap(
                        category -> String.valueOf(category.getId()), // Assuming Category has an getId() method.
                        CategoryMapper.INSTANCE::categoryToCategoryDTO));
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper.INSTANCE::categoryToCategoryDTO);
    }

    @Override
    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.INSTANCE.categoryDTOToCategory(categoryDTO);
        category = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setTitle(categoryDTO.getTitle());
        category = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO patch(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (categoryDTO.getTitle() != null) {
            category.setTitle(categoryDTO.getTitle());
        }

        category = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.categoryToCategoryDTO(category);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
