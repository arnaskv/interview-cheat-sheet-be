package com.interview.manager.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class CategoryDTO {

    private final Long id;

    @NotBlank(message = "Category title is required")
    @Size(max = 256, message = "Category title must be less than 256 characters.")
    private String title;
}
