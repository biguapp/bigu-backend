package com.api.bigu.dto.auth;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String fullName;
    @Pattern(regexp = "[\\w-.]+@([\\w-])+.ufcg.edu.br$", message = "Email not valid")
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
    private String userType;
}
