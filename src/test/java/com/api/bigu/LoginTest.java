package com.api.bigu;

import com.api.bigu.controllers.AuthenticationController;
import com.api.bigu.dto.auth.*;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.UserType;
import com.api.bigu.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import static com.api.bigu.models.enums.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    AuthenticationController authenticationController;

    @Test
    public void loginSucesso() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("Fulano de Tal", "fulano@ccc.ufcg.edu.br", "121110612", "83996798478", "senhaCerta", "USER", "RIDER");
        authenticationController.register(registerRequest);

        // Act
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(), registerRequest.getPassword());
        AuthenticationResponse authenticationResponse = authenticationController.authenticate(authenticationRequest).getBody();

        // Assert
        assertEquals(registerRequest.getEmail(), authenticationRequest.getEmail());
        assertEquals(registerRequest.getPassword(), authenticationRequest.getPassword());
        assertTrue(authenticationResponse.getToken() != null && !authenticationResponse.getToken().isEmpty());
    }

    @Test
    public void loginFalhaCredenciaisInvalidas() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("Fulano de Tal", "fulano@ccc.ufcg.edu.br", "121110612", "83996798478", "senha123", "USER", "RIDER");
        authenticationController.register(registerRequest);

        // Act
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(), "senhaErrada");
        AuthenticationResponse authenticationResponse = authenticationController.authenticate(authenticationRequest).getBody();

        // Assert
        assertNull(authenticationResponse);
        assertEquals(registerRequest.getEmail(), authenticationRequest.getEmail());
        assertEquals(registerRequest.getPassword(), authenticationRequest.getPassword());
        assertTrue(authenticationResponse.getToken() == null || authenticationResponse.getToken().isEmpty());

    }

    @Test
    public void loginBloqueioConta() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("Fulano de Tal", "fulano@ccc.ufcg.edu.br", "121110612", "83996798478", "senha123", "USER", "RIDER");
        authenticationController.register(registerRequest);
        boolean result = true;
        // Act
        for (int i = 0; i < 5; i++) {
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(registerRequest.getEmail(), "senhaErrada");
            authenticationController.authenticate(authenticationRequest);
            if (authenticationRequest == null){
                result = false;
            }
            else { result = true; }
            if (result == false && i == 4){
                //authenticationService.blockAuthenticate();
                //TODO terminar
            }
        }



        // Assert
        // assertFalse(result);
        //assertTrue(authenticationService.());
    }

    @Test
    public void recuperarSenha() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("Fulano de Tal", "fulano@ccc.ufcg.edu.br","121110612", "83996798478", "senha123", "USER", "RIDER");
        authenticationController.register(registerRequest);

        // Act
        RecoveryRequest recoveryRequest = new RecoveryRequest(registerRequest.getEmail());
        RecoveryResponse recoveryResponse = authenticationController.forgotPassword(recoveryRequest).getBody();

        // Assert
        assertTrue(recoveryResponse.getToken() != null);

    }

}
