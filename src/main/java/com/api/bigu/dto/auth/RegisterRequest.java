package com.api.bigu.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequest {

    @NonNull
	private String fullName;
    @NonNull
    private String email;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String password;

    private String role;
//    private String userType;

}
