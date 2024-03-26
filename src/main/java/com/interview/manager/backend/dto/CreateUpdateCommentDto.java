package com.interview.manager.backend.dto;

// import java.util.UUID;

// import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateUpdateCommentDto {
  // @NonNull
  // @Size(min=2, max=256)
  private String content;
  // private UUID questionId;
}
