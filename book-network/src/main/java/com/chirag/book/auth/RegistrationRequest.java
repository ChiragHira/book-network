package com.chirag.book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "First name is Mandatory")
    @NotBlank(message = "First name is Mandatory")
    private String firstname;

    @NotEmpty(message = "Last name is Mandatory")
    @NotBlank(message = "Last name is Mandatory")
    private String lastname;

    @Email(message = "Email is not formatted")
    @NotEmpty(message = "Email name is Mandatory")
    @NotBlank(message = "Email name is Mandatory")
    private String email;

    @Size(min=8, message = "Password should be 8 characters long minimum")
    private String password;
}
