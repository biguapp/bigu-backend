/**
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
@Schema(description = "Authentication Request")
public class AuthenticationRequest {

    @NotNull(message = "Email field cannot be null")
    @Pattern(regexp = "^[a-z0-9._]+@([a-z0-9])+\\.ufcg.edu.br$", message = "email not valid")
    @Schema(example = "aluno@ccc.ufcg.edu.br")
    private String email;
    @NotNull(message = "Password field cannot be null")
    private String password;



}
*/
