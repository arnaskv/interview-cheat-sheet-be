package com.interview.manager.backend.services.category;

import com.interview.manager.backend.dto.RequestCategoryDto;
import com.interview.manager.backend.dto.RequestEditCategoryDto;
import com.interview.manager.backend.dto.ResponseCategoryDto;
import com.interview.manager.backend.exceptions.DataValidationException;
import com.interview.manager.backend.models.Category;
import com.interview.manager.backend.repositories.CategoryRepository;
import com.interview.manager.backend.types.DataValidation;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private static final CategoryMapper CATEGORY_MAPPER = CategoryMapper.INSTANCE;

    private final CategoryRepository categoryRepository;

    @Override
    public List<ResponseCategoryDto> getAllCategoriesReversed() {
        List<Category> categories = categoryRepository.findAllByOrderByIdDesc();
        return categories.stream()
                .map(CATEGORY_MAPPER::categoryToResponseCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ResponseCategoryDto> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CATEGORY_MAPPER::categoryToResponseCategoryDTO);
    }

    @Override
    @Transactional(rollbackFor = DataValidationException.class)
    public ResponseCategoryDto createCategory(RequestCategoryDto requestCategoryDTO) {
        if (requestCategoryDTO.getTitle() == null || requestCategoryDTO.getTitle().isBlank()) {
            logger.error("Title is missing.");
            throw new DataValidationException(DataValidation.Status.MISSING_DATA);
        } else if (requestCategoryDTO.getTitle().length() > 256) {
            logger.error("Title '{}' is too long. Maximum allowed length is 256 characters.",
                    requestCategoryDTO.getTitle());
            throw new DataValidationException(DataValidation.Status.TITLE_TOO_LARGE);
        }
        Category category = CATEGORY_MAPPER.requestCategoryDTOToCategory(requestCategoryDTO);
        return CATEGORY_MAPPER.categoryToResponseCategoryDTO(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public ResponseCategoryDto editCategory(RequestEditCategoryDto requestCategoryDTO) {
        Category category = categoryRepository.findById(requestCategoryDTO.getId())
                .orElseThrow(() -> new DataValidationException(DataValidation.Status.CATEGORY_NOT_FOUND));
        logger.info("Editing category with ID: {}", category.getId());
        if (!category.getTitle().equals(requestCategoryDTO.getTitle())) {
            category.setTitle(requestCategoryDTO.getTitle());
            category = categoryRepository.save(category);
            logger.info("Category updated: {}", category.getId());
        }
        return CATEGORY_MAPPER.categoryToResponseCategoryDTO(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            logger.error("Attempt to delete non-existent category with ID: {}", id);
            throw new DataValidationException(DataValidation.Status.CATEGORY_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
        logger.info("Category deleted with ID: {}", id);
    }

}
