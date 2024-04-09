package com.interview.manager.backend.services.category;

import com.interview.manager.backend.dto.RequestCategoryDTO;
import com.interview.manager.backend.dto.ResponseCategoryDto;
import com.interview.manager.backend.models.Category;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    ResponseCategoryDto categoryToResponseCategoryDTO(Category category);

    Category responseCategoryDTOToCategory(ResponseCategoryDto responseCategoryDto);

    Category requestCategoryDTOToCategory(RequestCategoryDTO requestCategoryDTO);

}
