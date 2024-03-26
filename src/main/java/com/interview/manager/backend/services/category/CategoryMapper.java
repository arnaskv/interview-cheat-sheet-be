package com.interview.manager.backend.services.category;

import com.interview.manager.backend.dto.ResponseCategoryDto;
import com.interview.manager.backend.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    ResponseCategoryDto categoryToCategoryDTO(Category category);
    Category categoryDTOToCategory(ResponseCategoryDto responseCategoryDto);
}
