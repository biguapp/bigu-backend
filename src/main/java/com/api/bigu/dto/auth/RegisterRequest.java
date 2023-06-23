package com.api.bigu.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    @NotNull(message = "Full Name field cannot be null")
    @Schema(example = "Ada Lovelace")
	private String fullName;

    @NotNull(message = "Sex field cannot be null")
    @Schema(example = "M")
    private String sex;

    @NotNull(message = "Email field cannot be null")
    @Pattern(regexp = "^[a-z0-9._]+@([a-z0-9])+\\.ufcg.edu.br$", message = "Email is not valid")
    @Schema(example = "aluno@ccc.ufcg.edu.br")
    private String email;

    @NotNull(message = "Phone Number field cannot be null")
    @Schema(example = "(83)90000-0000")
    private String phoneNumber;

    @NotNull(message = "Password field cannot be null")
    private String password;

    @Schema(example = "user")
    private String role;

}
