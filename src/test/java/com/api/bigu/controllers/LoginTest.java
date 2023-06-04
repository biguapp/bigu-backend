//package com.api.bigu.controllers;
//
//import com.api.bigu.dto.auth.*;
//import com.api.bigu.exceptions.EmailException;
//import com.api.bigu.exceptions.UserNotFoundException;
//import com.api.bigu.exceptions.WrongPasswordException;
//import com.api.bigu.services.AuthenticationService;
//import com.api.bigu.services.UserService;
//import com.api.bigu.util.errors.UserError;
//import jakarta.transaction.Transactional;
//import lombok.NoArgsConstructor;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.mail.MessagingException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@NoArgsConstructor
//public class LoginTest {
//
//    @Autowired
//    AuthenticationController authenticationController;
//
//    @Autowired
//    AuthenticationService authenticationService;
//
//    @Autowired
//    UserService userService;
//
//    @Test
//    @Transactional
//    @Rollback()
//    public void loginSucesso() {
//        // Arrange
//        RegisterRequest registerRequest = RegisterRequest.builder()
//                .fullName("Fulano de Tal")
//                .email("fulano@ccc.ufcg.edu.br")
//                .phoneNumber("83996798478")
//                .password("senhaCerta")
//                .role("USER")
//                .build();
//        userService.buildUser(registerRequest);
//
//        // Act
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(), registerRequest.getPassword());
//        AuthenticationResponse authenticationResponse;
//        try {
//            authenticationResponse = authenticationService.authenticate(authenticationRequest);
//        } catch (UserNotFoundException e) {
//            throw new NullPointerException("Usuário não encontrado.");
//        } catch (WrongPasswordException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Assert
//        assertEquals(registerRequest.getEmail(), authenticationRequest.getEmail());
//        assertEquals(registerRequest.getPassword(), authenticationRequest.getPassword());
//        assertTrue(authenticationResponse.getToken() != null && !authenticationResponse.getToken().isEmpty());
//    }
//
//    @Test
//    @Transactional
//    @Rollback()
//    public void senhaErrada() {
//        // Arrange
//        RegisterRequest registerRequest = RegisterRequest.builder()
//                .fullName("Fulano de Tal")
//                .email("fulano@ccc.ufcg.edu.br")
//                .phoneNumber("83996798478")
//                .password("senha123")
//                .role("USER")
//                .build();
//        userService.buildUser(registerRequest);
//
//        // Act
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(), "senhaErrada");
//        AuthenticationResponse authenticationResponse;
//        try {
//            authenticationResponse = authenticationService.authenticate(authenticationRequest);
//        } catch (UserNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (BadCredentialsException e) {
//            authenticationResponse = null;
//        }
//
//        // Assert
//        assertNull(authenticationResponse);
//        assertEquals(registerRequest.getEmail(), authenticationRequest.getEmail());
//        assertNotEquals(registerRequest.getPassword(), authenticationRequest.getPassword());
//
//    }
//
//    @SneakyThrows
//    @Test
//    @Transactional
//    @Rollback()
//    public void loginBloqueioConta() {
//        // Arrange
//        RegisterRequest registerRequest = RegisterRequest.builder()
//                .fullName("Fulano de Tal")
//                .email("fulano@ccc.ufcg.edu.br")
//                .phoneNumber("83996798478")
//                .password("senha123")
//                .role("USER")
//                .build();
//        authenticationService.register(registerRequest);
//        boolean authenticated = true;
//        AuthenticationResponse authenticationResponse = null;
//        for (int i = 0; i < 5 && authenticated; i++) {
//            AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(), "senhaErrada");
//
//            try {
//                authenticationResponse = authenticationService.authenticate(authenticationRequest);
//            } catch (UserNotFoundException e) {
//                throw new RuntimeException(e);
//            } catch (BadCredentialsException e) {
//                authenticationResponse = null;
//            }
//            authenticated = authenticationResponse != null;
//        }
//        if (!authenticated) {
//            authenticationService.blockAuthenticate(registerRequest.getEmail());
//        }
//
//        assertTrue(userService.isBlocked(registerRequest.getEmail()));
//        assertNull(authenticationResponse);
//    }
//
//    @Test
//    @Transactional
//    @Rollback()
//    public void recuperarSenha() {
//        // Arrange
//        RegisterRequest registerRequest = RegisterRequest.builder()
//                .fullName("Fulano de Tal")
//                .email("fulano@ccc.ufcg.edu.br")
//                .phoneNumber("83996798478")
//                .password("senha123")
//                .role("USER")
//                .build();
//        authenticationService.register(registerRequest);
//
//        // Act
//        RecoveryResponse recoveryResponse;
//        try {
//            recoveryResponse = authenticationService.recover(registerRequest.getEmail());
//        } catch (UserNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Assert
//        assertNotNull(recoveryResponse.getToken());
//
//    }
//
//}
