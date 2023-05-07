package com.api.bigu.dto.auth;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationRequest {

    @NonNull
    @Pattern(regexp = "[\\w-.]+@([\\w-])+.ufcg.edu.+[\\w-]$", message = "email not valid")
    private String email;
    @NonNull
    private String password;
}
