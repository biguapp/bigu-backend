package com.api.bigu.controllers;

import com.api.bigu.dto.auth.*;
import com.api.bigu.exceptions.BadRequestExceptionHandler;
import com.api.bigu.exceptions.EmailException;
import com.api.bigu.exceptions.RegisterException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.services.AuthenticationService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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
            e.printStackTrace();
            throw new RegisterException(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<RecoveryResponse> forgotPassword(
            @RequestBody @Valid RecoveryRequest recoveryRequest
            ) {
        try {
            return ResponseEntity.ok(authenticationService.recover(recoveryRequest));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}
