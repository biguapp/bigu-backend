package com.api.bigu.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class NewPasswordRequest {

    @NonNull
    private String actualPassword;

    @NonNull
    private String newPassword;

    @NonNull
    private String newPasswordConfirmation;

}
