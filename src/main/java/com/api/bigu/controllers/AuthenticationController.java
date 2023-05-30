package com.api.bigu.controllers;

import com.api.bigu.dto.auth.*;
import com.api.bigu.exceptions.BadRequestExceptionHandler;
import com.api.bigu.exceptions.EmailException;
import com.api.bigu.exceptions.RegisterException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.User;
import com.api.bigu.services.AuthenticationService;
import com.api.bigu.services.UserService;
import com.api.bigu.util.errors.AuthenticationError;
import com.api.bigu.util.errors.UserError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Autowired
    AuthenticationService authenticationService;
	
	@Autowired
	UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest registerRequest
    ) {
        try {
            return ResponseEntity.ok(authenticationService.register(registerRequest));
        } catch (IllegalArgumentException | TransactionSystemException e) {
            return AuthenticationError.userUnauthorized(e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
        } catch (Exception e) {
            return AuthenticationError.userUnauthorized(e.getMessage());
        } catch (UserNotFoundException e) {
            return UserError.userNotFoundError();
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestParam @Pattern(regexp = "^[a-z0-9._]+@([a-z0-9])+\\.ufcg.edu.br$", message = "email not valid") String userEmail
            ) {
        try {
            return ResponseEntity.ok(authenticationService.recover(userEmail));
        } catch (UserNotFoundException unfe) {
            return UserError.userNotFoundError();
        } catch (MessagingException e) { //TODO: lidar com MessagingException
            throw new RuntimeException(e);
        }
    }
    
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> processResetPassword(HttpServletRequest request) throws UserNotFoundException {
    	String token = request.getParameter("token");
    	String password = request.getParameter("password");
    	
    	try {
	    	User user = userService.findUserByResetPasswordToken(token).get();
	    	userService.updatePassword(user, password);
	    	return ResponseEntity.ok("Senha modificada com sucesso");
    	} catch (UserNotFoundException unfe) {
            return UserError.userNotFoundError();
    	}
    	
    }
    

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            authenticationService.addToBlackList(token);
            return ResponseEntity.ok("Logout realizado com sucesso");
        } catch (Exception e) {
            return AuthenticationError.failedLogout(e.getMessage());
        }
    }
}
