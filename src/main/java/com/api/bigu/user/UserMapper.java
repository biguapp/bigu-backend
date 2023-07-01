package com.api.bigu.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User userCreated) {
        return UserResponse.builder()
                .userId(userCreated.getUserId())
                .fullName(userCreated.getFullName())
                .sex(userCreated.getSex())
                .email(userCreated.getEmail())
                .phoneNumber(userCreated.getPhoneNumber())
                .matricula(userCreated.getMatricula())
                .build();
    }
}
