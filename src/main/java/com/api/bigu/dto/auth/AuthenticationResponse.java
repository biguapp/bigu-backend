package com.api.bigu.dto.auth;

import com.api.bigu.dto.user.UserRequest;
import com.api.bigu.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;

    private UserResponse userResponse;

}
