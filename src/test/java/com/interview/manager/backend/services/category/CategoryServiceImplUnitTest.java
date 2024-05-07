package com.interview.manager.backend.services.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.interview.manager.backend.dto.RequestCategoryDto;
import com.interview.manager.backend.dto.RequestEditCategoryDto;
import com.interview.manager.backend.dto.ResponseCategoryDto;
import com.interview.manager.backend.exceptions.DataValidationException;
import com.interview.manager.backend.models.Category;
import com.interview.manager.backend.repositories.CategoryRepository;
import com.interview.manager.backend.types.DataValidation;

@ExtendWith(SpringExtension.class)
public class CategoryServiceImplUnitTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category1;
    private Category category2;
    private ResponseCategoryDto responseCategoryDTO1;
    private ResponseCategoryDto responseCategoryDTO2;

    @BeforeEach
    void setUp() {
        category1 = new Category("Category 1");
        category2 = new Category("Category 2");
        responseCategoryDTO1 = new ResponseCategoryDto(1L, "Category 1");
        responseCategoryDTO2 = new ResponseCategoryDto(2L, "Category 2");
    }

    @Test
    void testGetAllCategoriesReversed() {
        when(categoryRepository.findAllByOrderByIdDesc()).thenReturn(List.of(category2, category1));
        List<ResponseCategoryDto> listOfResponseCategoryDTOs = categoryService.getAllCategoriesReversed();
        assertEquals(2, listOfResponseCategoryDTOs.size());
        assertEquals(responseCategoryDTO2.getTitle(), listOfResponseCategoryDTOs.get(0).getTitle());
        assertEquals(responseCategoryDTO1.getTitle(), listOfResponseCategoryDTOs.get(1).getTitle());
    }

    @Test
    void testGetCategoryById_found() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        Optional<ResponseCategoryDto> result = categoryService.getCategoryById(1L);
        assertTrue(result.isPresent());
        assertEquals(responseCategoryDTO1.getTitle(), result.get().getTitle());
    }

    @Test
    void testGetCategoryById_notFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<ResponseCategoryDto> responseCategoryDTO = categoryService.getCategoryById(1L);
        assertFalse(responseCategoryDTO.isPresent());
    }

    @Test
    void createCategory_validData() {
        RequestCategoryDto requestCategoryDTO = new RequestCategoryDto("Category 3");
        Category savedCategory = new Category("Category 3");
        savedCategory.setId(3L);
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);
        ResponseCategoryDto result = categoryService.createCategory(requestCategoryDTO);
        assertEquals(requestCategoryDTO.getTitle(), result.getTitle());
        assertEquals(savedCategory.getId(), result.getId());
    }

    @Test
    void createCategory_missingTitle_emptyString() {
        RequestCategoryDto requestDto = new RequestCategoryDto("");
        Exception exception = assertThrows(DataValidationException.class, () -> {
            categoryService.createCategory(requestDto);
        });
        assertEquals(DataValidation.Status.MISSING_DATA, ((DataValidationException) exception).getStatus());
    }

    @Test
    void createCategory_missingTitle_null() {
        RequestCategoryDto requestDto = new RequestCategoryDto(null);
        Exception exception = assertThrows(DataValidationException.class, () -> {
            categoryService.createCategory(requestDto);
        });
        assertEquals(DataValidation.Status.MISSING_DATA, ((DataValidationException) exception).getStatus());
    }

    @Test
    void createCategory_titleTooLong() {
        RequestCategoryDto requestDto = new RequestCategoryDto("a".repeat(257));
        Exception exception = assertThrows(DataValidationException.class, () -> {
            categoryService.createCategory(requestDto);
        });
        assertEquals(DataValidation.Status.TITLE_TOO_LARGE, ((DataValidationException) exception).getStatus());
    }

    @Test
    void editCategory_existingCategory() {
        Category updatedCategory = new Category("Updated Category");
        updatedCategory.setId(1L);
        RequestEditCategoryDto editDTO = new RequestEditCategoryDto(1L, "Updated Category");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
        ResponseCategoryDto result = categoryService.editCategory(editDTO);
        assertNotNull(result);
        assertEquals(editDTO.getTitle(), result.getTitle());
    }

    @Test
    void editCategory_sameCategoryDataChange() {
        RequestEditCategoryDto editDTO = new RequestEditCategoryDto(1L, "Category 1");
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        ResponseCategoryDto result = categoryService.editCategory(editDTO);
        assertNotNull(result);
        assertEquals(editDTO.getTitle(), result.getTitle());
    }

    @Test
    void editCategory_nonExistingCategory() {
        RequestEditCategoryDto editDTO = new RequestEditCategoryDto(3L, "Updated Category");
        when(categoryRepository.findById(editDTO.getId())).thenReturn(Optional.empty());
        Exception exception = assertThrows(DataValidationException.class, () -> {
            categoryService.editCategory(editDTO);
        });
        assertTrue(exception.getMessage().contains("Category not found"));
    }

    @Test
    void deleteCategory_existingCategory() {
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(categoryId);
        assertDoesNotThrow(() -> categoryService.deleteCategoryById(categoryId));
        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    void deleteCategory_nonExistingCategory() {
        Long categoryId = 3L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);
        Exception exception = assertThrows(DataValidationException.class, () -> {
            categoryService.deleteCategoryById(categoryId);
        });
        assertTrue(exception.getMessage().contains("Category not found"));
    }
}
