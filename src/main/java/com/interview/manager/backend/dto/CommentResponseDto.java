package com.interview.manager.backend.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class CommentResponseDto {
    @NotBlank
    private UUID id;
    @NotBlank
    @Size(min = 1, max = 255)
    private String content;
    @PastOrPresent
    private OffsetDateTime dateCreated;
    @PastOrPresent
    private OffsetDateTime dateModified;
}
