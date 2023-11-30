package com.api.bigu.dto.user;

import com.api.bigu.dto.user.UserResponse;
import com.api.bigu.models.User;
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
                .avgScore(userCreated.getAvgScore())
                .build();
    }
}