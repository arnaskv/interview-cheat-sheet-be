package com.interview.manager.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder(toBuilder = true)
public class UserRegisterRequestDto {

    @NotBlank(message = "Name should not be blank")
    private String name;

    @NotBlank(message = "Name should not be blank")
    private String surname;

    @Email
    @NotBlank(message = "Email should not be blank")
    private String email;

    @NotBlank(message = "Password should not be blank")
    @Size(min = 7, message = "Password should be at least 7 characters long")
    private String password;
}
