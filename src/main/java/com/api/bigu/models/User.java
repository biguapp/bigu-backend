package com.api.bigu.models;

import com.api.bigu.models.enums.Addresses;
import com.api.bigu.models.enums.Role;
import com.api.bigu.models.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "TB_USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String profileImageName;

    private String profileImageType;

    @Lob
    private byte[] profileImage;

    @CPF
    @Column(name = "cpf_user")
    private String cpfUser;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "email", nullable = false, unique = true)
    @Pattern(regexp = "[\\w-.]+@([\\w-])+.ufcg.edu.br$", message = "email not valid")
    private String email;

    @Column(name="matricula")
    private String matricula;

    @Column(name="phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "resetPasswordToken")
    private String resetPasswordToken;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "userId")
    @Column(name = "addresses")
    private Map<String, Address> addresses;

    @OneToMany(mappedBy = "userId")
    @Column(name = "cars")
    private Map<Integer, Car> cars;

    @ManyToMany(mappedBy = "members")
    @JsonIgnore
    private List<Ride> rides;

    @Builder.Default
    private boolean accountNonLocked = true;

    @Column(name = "validated")
    private boolean isValidated;

    @Column(name = "userValidateToken")
    private String userValidateToken;

    @OneToMany
    private List<Feedback> feedbacks;

    @Column(name = "avgScore")
    private float avgScore;

    private static final int MAX_LOGIN_ATTEMPTS = 5;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
		this.password = password;
	}

	@Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUserId(), user.getUserId()) && Objects.equals(getCpfUser(), user.getCpfUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId(), this.getCpfUser());
    }
}
