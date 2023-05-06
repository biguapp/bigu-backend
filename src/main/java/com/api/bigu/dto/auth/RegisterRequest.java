package com.api.bigu.dto.auth;

import com.api.bigu.models.enums.Role;
import jakarta.validation.constraints.Email;
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
    @Pattern(regexp = "[\\w-.]+@([\\w-])+.ufcg.edu.+[\\w-]$", message = "email not valid")
    private String email;
    private String phoneNumber;
    private String password;
    private Role role;
}
