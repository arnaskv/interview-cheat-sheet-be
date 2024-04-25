package com.interview.manager.backend.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private UUID id;
    @NotNull
    @Size(min = 2, max = 255)
    private String content;
    @PastOrPresent
    private OffsetDateTime dateCreated;
    @PastOrPresent
    private OffsetDateTime dateModified;
}
