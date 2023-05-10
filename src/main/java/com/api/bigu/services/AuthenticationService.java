package com.api.bigu.services;

import com.api.bigu.dto.auth.*;
import com.api.bigu.config.JwtService;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.Role;
import com.api.bigu.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if(registerRequest.getRole() == null) {
            registerRequest.setRole(Role.USER.toString().toUpperCase());
        }
        var user = User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .matricula(registerRequest.getMatricula())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.valueOf(registerRequest.getRole().toUpperCase()))
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            )
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        if (!user.getPassword().equals(authenticationRequest.getPassword())) {
            LoginAttemptService.incrementLoginAttempts(authenticationRequest.getEmail());
        } else { LoginAttemptService.resetLoginAttempts(authenticationRequest.getEmail()); }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public RecoveryResponse recover(RecoveryRequest recoveryRequest) {
        var user = userRepository.findByEmail(recoveryRequest.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return RecoveryResponse.builder()
                .token(jwtToken)
                .build();
    }
}
