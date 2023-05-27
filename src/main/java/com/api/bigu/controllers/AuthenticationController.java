package com.api.bigu.controllers;

import com.api.bigu.dto.auth.*;
import com.api.bigu.exceptions.BadRequestExceptionHandler;
import com.api.bigu.exceptions.EmailException;
import com.api.bigu.exceptions.RegisterException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {
        try {
            return ResponseEntity.ok(authenticationService.register(registerRequest));
        } catch (IllegalArgumentException | TransactionSystemException e) {
            throw new RegisterException(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
        } catch (Exception e) {
            throw new RegisterException(e.getMessage());
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<RecoveryResponse> forgotPassword(
            @RequestBody @Valid RecoveryRequest recoveryRequest
            ) {
        try {
            return ResponseEntity.ok(authenticationService.recover(recoveryRequest));
        } catch (UserNotFoundException | EmailException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            authenticationService.addToBlackList(token);
            return ResponseEntity.ok("Logout realizado com sucesso");
        } catch (Exception e) {
            throw new RegisterException(e.getMessage());
        }
    }
}
