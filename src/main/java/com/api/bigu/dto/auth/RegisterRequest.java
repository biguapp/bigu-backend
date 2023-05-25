package com.api.bigu.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    @NonNull
    @Schema(example = "Ada Lovelace")
	private String fullName;

    @NonNull
    @Pattern(regexp = "^[a-z0-9._]+@([a-z0-9])+\\.ufcg.edu.br$", message = "email not valid")
    @Schema(example = "aluno@ccc.ufcg.edu.br")
    private String email;

    @NonNull
    @Schema(example = "(DD)90000-0000")
    private String phoneNumber;

    @NonNull
    private String password;

    @Schema(example = "user")
    private String role;
//    private String userType;

}
