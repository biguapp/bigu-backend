package com.api.bigu.services;

import com.api.bigu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginAttemptService {

    @Autowired
    private static UserRepository userRepository;


    public static void incrementLoginAttempts(String email) {
        userRepository.findByEmail(email).get().loginFailed();
    }

    public static void resetLoginAttempts(String email) {
        userRepository.findByEmail(email).get().loginSucceeded();
    }
}
