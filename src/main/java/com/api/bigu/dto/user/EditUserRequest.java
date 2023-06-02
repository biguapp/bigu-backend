package com.api.bigu.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String matricula;

}
