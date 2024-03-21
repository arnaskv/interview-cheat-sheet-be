package com.interview.manager.backend.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentDto {
  private UUID id;
  private String content;
  // private UUID questionId;
  private OffsetDateTime dateCreated;
  private OffsetDateTime dateModified;
}
