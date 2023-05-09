package com.api.bigu;

import com.api.bigu.dto.auth.*;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.UserType;
import com.api.bigu.repositories.UserRepository;
import com.api.bigu.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import static com.api.bigu.models.enums.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
	
	AuthenticationService authenticationService;
	UserRepository userRepository;
	
	@Test
	public void cadastroSucesso() {
		
		RegisterRequest registerRequest = new RegisterRequest("Beltrano da Silva", "beltrano@ccc.ufcg.edu.br", "120123456", "83991234567", "senha123", "USER", "RIDER");
		authenticationService.register(registerRequest);
		
		
		assertTrue(userRepository.findByEmail(registerRequest.getEmail()) != null);
	}
	
	@Test
	public void cadastroEmailVazio() { //falha
		RegisterRequest registerRequest = new RegisterRequest("Beltrano da Silva", null, "120123456", "83991234567", "senha123", "USER", "RIDER");
		AuthenticationResponse registerResponse = authenticationService.register(registerRequest);
		
		assertNull(registerResponse);
	}
	
	@Test
	public void cadastroEmailInvalido() { //falha
		RegisterRequest registerRequest = new RegisterRequest("Beltrano da Silva", "beltrano@gmail.com", "120123456", "83991234567", "senha123", "USER", "RIDER");
		AuthenticationResponse registerResponse = authenticationService.register(registerRequest);
		
		assertNull(registerResponse);
	}
	
	@Test
	public void cadastroMatriculaVazio() { //falha
		RegisterRequest registerRequest = new RegisterRequest("Beltrano da Silva", "beltrano@ccc.ufcg.edu.br", null, "83991234567", "senha123", "USER", "RIDER");
		AuthenticationResponse registerResponse = authenticationService.register(registerRequest);
		
		assertNull(registerResponse);
	}
	
	@Test
	public void cadastroTelefoneVazio() { //falha
		RegisterRequest registerRequest = new RegisterRequest("Beltrano da Silva", "beltrano@ccc.ufcg.edu.br", "120123456", null, "senha123", "USER", "RIDER");
		AuthenticationResponse registerResponse = authenticationService.register(registerRequest);
		
		assertNull(registerResponse);
	}
	
}
