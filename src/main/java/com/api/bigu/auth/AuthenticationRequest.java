package com.api.bigu.auth;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @Pattern(regexp = "[\\w-.]+@([\\w-])+.ufcg.edu.+[\\w-]$", message = "email not valid")
    private String email;
    private String password;
}
