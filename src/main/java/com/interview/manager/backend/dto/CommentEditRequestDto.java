package com.interview.manager.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
public class CommentEditRequestDto {

    @NotNull(message = "Id should not be null")
    private UUID id;

    @NotBlank(message = "Comment should not be blank")
    @Size(max = 255, min = 1, message = "Comment should be between {min} and {max} characters")
    private String content;
}
