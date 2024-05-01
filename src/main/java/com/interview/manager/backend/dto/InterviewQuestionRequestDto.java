package com.interview.manager.backend.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
public class InterviewQuestionRequestDto {
    @NotBlank(message = "Title should not be blank")
    @Size(max = 256, min = 1, message = "Title should be between {min} and {max} characters")
    private String title;

    private Long categoryId;

    @Nullable
    private Long parentId;
}
