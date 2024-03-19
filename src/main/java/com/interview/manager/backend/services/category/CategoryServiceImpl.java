package com.interview.manager.backend.services.category;

import com.interview.manager.backend.dto.response.CategoryDTO;
import com.interview.manager.backend.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final CategoryMapper CATEGORY_MAPPER = CategoryMapper.INSTANCE;

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CATEGORY_MAPPER::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDTO> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CATEGORY_MAPPER::categoryToCategoryDTO);
    }
}
