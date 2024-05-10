package com.interview.manager.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserLoginResponseDto {

    @NotBlank
    private String token;

    @NotBlank
    private String refreshToken;
}
