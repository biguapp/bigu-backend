/**
package com.api.bigu.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class NewPasswordRequest {

    @NotNull(message = "New Password field cannot be null")
    private String newPassword;

    @NotNull(message = "Password Confirmation field cannot be null")
    private String newPasswordConfirmation;

}
*/
