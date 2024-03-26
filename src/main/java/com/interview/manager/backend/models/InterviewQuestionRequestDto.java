package com.interview.manager.backend.models;

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
    @NotBlank
    @Size(max = 50)
    private String title;

//    @NotBlank
//    private Interview interview;
}