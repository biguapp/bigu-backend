package com.api.bigu;

import com.api.bigu.dto.auth.*;
import com.api.bigu.models.User;
import com.api.bigu.models.enums.UserType;
import com.api.bigu.repositories.UserRepository;
import com.api.bigu.services.AuthenticationService;
import com.api.bigu.services.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import static com.api.bigu.models.enums.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
	
	AuthenticationService authenticationService;
	UserRepository userRepository;
	UserService userService;
	
	@Test
	public void cadastroSucesso() {
		RegisterRequest registerRequest = RegisterRequest.builder()
				.fullName("Beltrano da Silva")
				.email("beltrano@ccc.ufcg.edu.br")
				.phoneNumber("83991234567")
				.password("senha123")
				.role("USER")
				.build();
		authenticationService.register(registerRequest);
		
		
		assertTrue(userRepository.findByEmail(registerRequest.getEmail()) != null);
	}
	
	@Test
	public void cadastroEmailVazio() { //falha
		RegisterRequest registerRequest = RegisterRequest.builder()
				.fullName("Beltrano da Silva")
				.email(null)
				.phoneNumber("83991234567")
				.password("senha123")
				.role("USER")
				.build();
		AuthenticationResponse registerResponse = authenticationService.register(registerRequest);
		
		assertNull(registerResponse);
	}
	
	@Test
	public void cadastroEmailInvalido() { //falha
		RegisterRequest registerRequest = RegisterRequest.builder()
				.fullName("Beltrano da Silva")
				.email("beltrano@gmail.com")
				.phoneNumber("83991234567")
				.password("senha123")
				.role("USER")
				.build();
		AuthenticationResponse registerResponse = authenticationService.register(registerRequest);
		
		assertNull(registerResponse);
	}
	
	@Test
	public void cadastroTelefoneVazio() { //falha
		RegisterRequest registerRequest = RegisterRequest.builder()
				.fullName("Beltrano da Silva")
				.email("beltrano@ccc.ufcg.edu.br")
				.phoneNumber(null)
				.password("senha123")
				.role("USER")
				.build();
		AuthenticationResponse registerResponse = authenticationService.register(registerRequest);
		
		assertNull(registerResponse);
	}
	
	@Test
	public void remocaoUsuarioSucesso() {
		RegisterRequest registerRequest = RegisterRequest.builder()
				.fullName("Beltrano da Silva")
				.email("beltrano@ccc.ufcg.edu.br")
				.phoneNumber("83991234567")
				.password("senha123")
				.role("USER")
				.build();
		authenticationService.register(registerRequest);
		
		Integer userId = userService.buildUser(registerRequest);
		userService.deleteById(userId);
		
		assertFalse(userRepository.existsById(userId));
	}
	
}
