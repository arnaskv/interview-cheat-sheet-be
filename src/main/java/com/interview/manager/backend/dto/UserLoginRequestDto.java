package com.interview.manager.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
public class UserLoginRequestDto {

    @Email
    @NotBlank(message = "Email should not be blank")
    private String email;

    @NotBlank(message = "Password should not be blank")
    private String password;
}
