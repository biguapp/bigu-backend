package com.api.bigu.controllers;

import com.api.bigu.dto.auth.*;
import com.api.bigu.exceptions.EmailException;
import com.api.bigu.exceptions.UserNotFoundException;
import com.api.bigu.services.AuthenticationService;
import com.api.bigu.services.UserService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@NoArgsConstructor
public class LoginTest {

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @Test
    @Transactional
    @Rollback()
    public void loginSucesso() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("Fulano de Tal", "fulano@ccc.ufcg.edu.br", "83996798478", "senhaCerta", "USER", "RIDER");
        userService.buildUser(registerRequest);

        // Act
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(), registerRequest.getPassword());
        AuthenticationResponse authenticationResponse;
        try {
            authenticationResponse = authenticationService.authenticate(authenticationRequest);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertEquals(registerRequest.getEmail(), authenticationRequest.getEmail());
        assertEquals(registerRequest.getPassword(), authenticationRequest.getPassword());
        assertTrue(authenticationResponse.getToken() != null && !authenticationResponse.getToken().isEmpty());
    }

    @Test
    @Transactional
    @Rollback()
    public void senhaErrada() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("Fulano de Tal", "fulano@ccc.ufcg.edu.br", "83996798478", "senha123", "USER", "RIDER");
        userService.buildUser(registerRequest);

        // Act
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(), "senhaErrada");
        AuthenticationResponse authenticationResponse;
        try {
            authenticationResponse = authenticationService.authenticate(authenticationRequest);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (BadCredentialsException e) {
            authenticationResponse = null;
        }

        // Assert
        assertNull(authenticationResponse);
        assertEquals(registerRequest.getEmail(), authenticationRequest.getEmail());
        assertNotEquals(registerRequest.getPassword(), authenticationRequest.getPassword());

    }

    @SneakyThrows
    @Test
    @Transactional
    @Rollback()
    public void loginBloqueioConta() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("Fulano de Tal", "fulano@ccc.ufcg.edu.br", "83996798478", "senha123", "USER", "RIDER");
        authenticationService.register(registerRequest);
        boolean authenticated = true;
        AuthenticationResponse authenticationResponse = null;
        for (int i = 0; i < 5 && authenticated; i++) {
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(), "senhaErrada");

            try {
                authenticationResponse = authenticationService.authenticate(authenticationRequest);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            } catch (BadCredentialsException e) {
                authenticationResponse = null;
            }
            authenticated = authenticationResponse != null;
        }
        if (!authenticated) {
            authenticationService.blockAuthenticate(registerRequest.getEmail());
        }

        assertTrue(userService.isBlocked(registerRequest.getEmail()));
        assertNull(authenticationResponse);
    }

    @Test
    @Transactional
    @Rollback()
    public void recuperarSenha() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("Fulano de Tal", "fulano@ccc.ufcg.edu.br", "83996798478", "senha123", "USER", "RIDER");
        authenticationService.register(registerRequest);

        // Act
        RecoveryRequest recoveryRequest = new RecoveryRequest(registerRequest.getEmail());
        RecoveryResponse recoveryResponse;
        try {
            recoveryResponse = authenticationService.recover(recoveryRequest);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertNotNull(recoveryResponse.getToken());

    }

}
