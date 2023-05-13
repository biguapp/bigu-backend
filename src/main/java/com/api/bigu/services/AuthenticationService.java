package com.api.bigu.services;

import com.api.bigu.dto.auth.*;
import com.api.bigu.config.JwtService;
import com.api.bigu.exceptions.EmailException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class AuthenticationService {


    private final EmailService emailService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if(registerRequest.getRole() == null) {
            registerRequest.setRole(Role.USER.toString().toUpperCase());
        }

        var user = userService.registerUser(User.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
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
        var jwtToken = jwtService.generateToken(user);
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
            userService.findUserByEmail(email).get().loginFailed();
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
