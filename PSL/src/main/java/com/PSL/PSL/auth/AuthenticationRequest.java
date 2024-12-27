package com.PSL.PSL.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationRequest {

    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email est obligatoire")
    @NotNull(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Password est obligatoire")
    @NotNull(message = "Password est obligatoire")
    @Size(min = 8, message = "Password doit contenir 8 caractere em minimum")
    private String password;
}