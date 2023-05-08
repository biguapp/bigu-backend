package com.api.bigu.models;

import com.api.bigu.models.enums.Role;
import com.api.bigu.models.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "TB_USER")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @CPF
    private String cpfUser;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    @Pattern(regexp = "[\\w-.]+@([\\w-])+.ufcg.edu.br$", message = "email not valid")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name="user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUserId(), user.getUserId()) && Objects.equals(getCpfUser(), user.getCpfUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getCpfUser());
    }
}
