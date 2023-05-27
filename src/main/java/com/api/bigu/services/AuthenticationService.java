package com.api.bigu.services;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.auth.*;
import com.api.bigu.exceptions.EmailException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class AuthenticationService {

	@Autowired
    private EmailService emailService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
    private JwtService jwtService;
	
	@Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if(registerRequest.getRole() == null) {
            registerRequest.setRole(Role.USER.toString().toUpperCase());
        }

        var user = userService.registerUser(User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .sex(registerRequest.getSex())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.valueOf(registerRequest.getRole().toUpperCase()))
                .build());

        var claims = new HashMap<String, Integer>();
        claims.put("uid", user.getUserId());

        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws UserNotFoundException, BadCredentialsException {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            )
        );
        var user = userService.findUserByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        if (!user.getPassword().equals(authenticationRequest.getPassword())) {
            incrementLoginAttempts(authenticationRequest.getEmail());
        } else { resetLoginAttempts(authenticationRequest.getEmail()); }

        var claims = new HashMap<String, Integer>();
        claims.put("uid", user.getUserId());

        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public RecoveryResponse recover(RecoveryRequest recoveryRequest) throws UserNotFoundException, EmailException {
        var user = userService.findUserByEmail(recoveryRequest.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        var recoveryLink = "https://example.com/recover?token=" + jwtToken;

        var subject = "Recuperação de senha";
        var body = "Olá " + user.getFullName() + ",\n\n" +
                "Recebemos uma solicitação de recuperação de senha para sua conta em nosso sistema. " +
                "Clique no link abaixo para criar uma nova senha:\n\n" +
                recoveryLink + "\n\n" +
                "Se você não solicitou a recuperação de senha, por favor, desconsidere este e-mail.\n\n" +
                "Atenciosamente,\n" +
                "Equipe do Sistema";

        emailService.sendEmail(user.getEmail(), subject, body);

        return RecoveryResponse.builder()
                .message("Um e-mail com instruções de recuperação foi enviado para " + user.getEmail())
                .build();
    }

    public void incrementLoginAttempts(String email) {
        if (userService.findUserByEmail(email).isPresent()) {
            userService.findUserByEmail(email).get().loginFailed();
        }
    }

    public void resetLoginAttempts(String email) {
        if (userService.findUserByEmail(email).isPresent()) {
            userService.findUserByEmail(email).get().loginSucceeded();
        }
    }

    public void blockAuthenticate(String email) {
        User user;
        try {
            user = userService.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        user.loginFailed();
        userService.updateUser(user);
    }

    public void sendConfirmationEmail(String to, String code) throws EmailException {
        String subject = "Confirmation code for your account";
        String body = "Your confirmation code is: " + code;
        emailService.sendEmail(to, subject, body);
    }
}
