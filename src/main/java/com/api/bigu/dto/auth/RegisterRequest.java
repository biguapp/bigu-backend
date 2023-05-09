package com.api.bigu.dto.auth;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	private String fullName;
    @Pattern(regexp = "[\\w-.]+@([\\w-])+.ufcg.edu.br$", message = "Email not valid")
    private String email;
    private String matricula;
    private String phoneNumber;
    private String password;
    private String role;
    private String userType;

    public RegisterRequest(String fullName, String email, String matricula, String phoneNumber, String password, String role, String userType) {
    	setFullName(fullName);
    	setEmail(email);
    	setMatricula(matricula);
    	setPhoneNumber(phoneNumber);
    	setPassword(password);
    	setRole(role);
    	setUserType(userType);
	}
    
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.fullName = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
