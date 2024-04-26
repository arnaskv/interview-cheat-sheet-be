package com.interview.manager.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestEditCategoryDto {

    @NotNull
    private Long id;

    @NotBlank(message = "Title should not be blank")
    @Size(max = 256, min = 1, message = "Title should be between {min} and {max} characters")
    private String title;
}
