package com.api.bigu.services;

import com.api.bigu.config.JwtService;
import com.api.bigu.dto.auth.*;
import com.api.bigu.dto.user.UserResponse;
import com.api.bigu.exceptions.EmailException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.exceptions.WrongPasswordException;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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

        UserResponse userResp = userService.toResponse(user);

        var claims = new HashMap<String, Integer>();
        claims.put("uid", user.getUserId());

        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userResponse(userResp)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws UserNotFoundException, WrongPasswordException {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            )
        );
        var user = userService.findUserByEmail(authenticationRequest.getEmail());
        if (!user.getPassword().equals(authenticationRequest.getPassword())) {
            incrementLoginAttempts(authenticationRequest.getEmail());
        } else { resetLoginAttempts(authenticationRequest.getEmail()); }

        var claims = new HashMap<String, Integer>();
        claims.put("uid", user.getUserId());

        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .userResponse(userService.toResponse((userService.findUserByEmail(authenticationRequest.getEmail()))))
                .token(jwtToken)
                .build();
    }

    public RecoveryResponse recover(String userEmail) throws UserNotFoundException, MessagingException {
        User user = userService.findUserByEmail(userEmail);

        String jwtToken = jwtService.generateToken(user);
        
        userService.updateResetPasswordToken(jwtToken, userEmail);
        
        String recoveryLink = "https://bigu.herokuapp.com/recover?token=" + jwtToken;

        String subject = "BIGU - Recuperação de senha";
        String body = "Olá " + user.getFullName() + ",\n\n" +
                "Recebemos uma solicitação de recuperação de senha para sua conta em nosso sistema. " +
                "Clique no link abaixo para criar uma nova senha:\n\n" +
                recoveryLink + "\n\n" +
                "Se você não solicitou a recuperação de senha, por favor, desconsidere este e-mail.\n\n" +
                "Atenciosamente,\n" +
                "Equipe do Sistema";

        emailService.sendEmail(user.getEmail(), subject, body);

        return new RecoveryResponse().builder()
                .message("Email enviado.")
                .build();
    }

    public void updatePassword(Integer userId, String actualPassword, NewPasswordRequest newPasswordRequest) throws WrongPasswordException, UserNotFoundException {
        User user = userService.findUserById(userId);
        String encodedNewPassword = "";

        if (passwordEncoder.matches(actualPassword, user.getPassword()) && newPasswordRequest.getNewPassword().equals(newPasswordRequest.getNewPasswordConfirmation())){
            encodedNewPassword = passwordEncoder.encode(newPasswordRequest.getNewPassword());
        } else throw new WrongPasswordException("Senha incorreta.");

        userService.updatePassword(userId, encodedNewPassword);
    }

    public void updatePassword(Integer userId, NewPasswordRequest newPasswordRequest) throws WrongPasswordException, UserNotFoundException {
        User user = userService.findUserById(userId);
        String encodedNewPassword = "";

        if (newPasswordRequest.getNewPassword().equals(newPasswordRequest.getNewPasswordConfirmation())){
            encodedNewPassword = passwordEncoder.encode(newPasswordRequest.getNewPassword());
        } else throw new WrongPasswordException("Senha incorreta.");

        userService.updatePassword(userId, encodedNewPassword);
    }

    public void incrementLoginAttempts(String email) throws UserNotFoundException {
        userService.findUserByEmail(email).loginFailed();
    }

    public void resetLoginAttempts(String email) throws UserNotFoundException {
        userService.findUserByEmail(email).loginSucceeded();
    }

    public void blockAuthenticate(String email) throws UserNotFoundException {
        User user;
        try {
            user = userService.findUserByEmail(email);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        user.loginFailed();
    }

    public void sendConfirmationEmail(String to, String code) throws MessagingException {
        String subject = "Confirmation code for your account";
        String body = "Your confirmation code is: " + code;
        emailService.sendEmail(to, subject, body);
    }

    public void addToBlackList(String token) {
        jwtService.addToBlacklist(token);
    }
}
