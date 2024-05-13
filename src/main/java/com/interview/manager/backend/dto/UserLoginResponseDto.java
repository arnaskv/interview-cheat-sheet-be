package com.interview.manager.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class UserLoginResponseDto {

    @NotBlank
    private String token;

    @NotBlank
    private String refreshToken;
}
